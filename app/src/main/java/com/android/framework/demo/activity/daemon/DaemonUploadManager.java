package com.android.framework.demo.activity.daemon;

import android.support.annotation.MainThread;
import android.support.annotation.Nullable;

import com.android.framework.util.LogUtil;
import com.android.framework.util.StringUtil;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 后台上传图片、视频管理类
 * Created by meikai on 2018/05/31.
 */
public class DaemonUploadManager {

    private static final String TAG = "DaemonUploadManager";

    private static final class Singleton {
        private static final DaemonUploadManager instance = new DaemonUploadManager();
    }

    private DaemonUploadManager() {
    }

    public static DaemonUploadManager getInstance() {
        return Singleton.instance;
    }

    /**
     * 等待后台上传的本地图片或视频 队列
     * 上传成功后，从此队列中移除
     * 确保线程安全，并且为队列模式，先add的会被先上传
     */
    private SourceQueue sourceQueue = new SourceQueue();

    /**
     * 已经上传成功的图片或视频的 路径映射表
     * 确保线程安全
     */
    private Map<String, DaemonResult> successMap = new ConcurrentHashMap<>();

    /**
     * glide的上传文件请求被网络IO操作阻塞住时，interrupted消费者线程，这时glide会抛出 InterruptedIOException，
     * 但core的BaseApi直接将所有api请求失败的IOException转义为 HttpException，导致应用层无法通过捕获 InterruptedIOException 来退出线程
     * 阻塞的线程也不能通过 Thread.interrupted() 标记来退出线程，因此只能自定义flag;
     */
    private boolean stopFlag = false;

    private Thread uploadThread;
    private Object mutex = new Object();

    @MainThread
    public void addImage(String localImagePath) {
        if (StringUtil.isEmpty(localImagePath)) {
            return;
        }
        if (successMap.get(localImagePath) != null) {
            return;
        }

        DaemonSource imageSource = DaemonSource.image(localImagePath);
        if (sourceQueue.contains(imageSource)) {
            return;
        }
        sourceQueue.enqueue(imageSource);

        resume();
    }

    @MainThread
    public void addVideo(String localImagePath) {
        if (StringUtil.isEmpty(localImagePath)) {
            return;
        }
        if (successMap.get(localImagePath) != null) {
            return;
        }
        DaemonSource videoSource = DaemonSource.video(localImagePath);
        if (sourceQueue.contains(videoSource)) {
            return;
        }
        sourceQueue.enqueue(videoSource);

        resume();
    }

    private void resume() {
        /**
         * 必须写在最外层，防止重复打开后台上传时，上次消费者线程还没有return，而stopFlag仍然为true的错乱情况
         */
        stopFlag = false;

        if (uploadThread == null || uploadThread.getState() == Thread.State.TERMINATED) {
            uploadThread = new Thread(uploadRunnable);
        }
        if (!uploadThread.isAlive()) {
            uploadThread.start();
        } else {
            try {
                synchronized (mutex) {
                    mutex.notify();
                }
            } catch (Exception e) {
                LogUtil.d(TAG, "notify:" + e.getMessage());
            }
        }
    }

    /**
     * 不再需要使用缓存记录时 调用
     */
    @MainThread
    public void stop() {
        sourceQueue.clear();
        successMap.clear();

        /**
         * 必须同时保留 stopFlag 和 interrupt() 两种方式来退出消费者线程
         * 这两种方式都有自己的弊端，但又互相补充
         * 1、stopFlag无法退出被 mutex.wait() 阻塞的线程
         * 2、interrupt()无法被 glide上传在网络IO阻塞 时被中断，因为BaseApi强行将 InterruptedIOException 转义成 HttpException，
         *    导致应用导无法识别 InterruptedIOException，也就不知道在何时 return 退出线程
         */
        stopFlag = true;
        if (uploadThread != null && uploadThread.isAlive()) {
            LogUtil.d(TAG, "stop 中断消费者线程");
            try {
                uploadThread.interrupt(); //阻塞状态的线程，无法通过interrupted标记，只能通过捕获异常来退出线程
            } catch (Exception e) {
                LogUtil.d(TAG, "interrupt:" + e.getMessage());
            }
        }
    }

    private Runnable uploadRunnable = new Runnable() {
        @Override
        public void run() {
            DaemonSource source;
            while (true) {
                if (uploadThread.isInterrupted()) {
                    // 正在运行时被中止，但队列里还有其它数据，必须通过此标记来退出线程；阻塞状态下此标记无法生效，只能通过捕获异常后return来退出线程
                    LogUtil.d(TAG, "消费者线程被主动interrupted，退出线程");
                    return;
                }

                if (stopFlag) {
                    //被网络IO阻塞的glide请求，无法通过interrupted来退出线程，而 InterruptedIOException 又被BaseApi转义为 HttpException
                    LogUtil.d(TAG, "消费者线程被自定义的线程退出标记识别，并退出消费者线程");
                    return;
                }

                source = sourceQueue.dequeue();

                if (source == null) {
                    try {
                        // 当无数据可用时，消费者线程进入阻塞状态，等待生产者入队新数据
                        LogUtil.d(TAG, "当前队列无数据可用，消费者线程进入阻塞状态");
                        synchronized (mutex) {
                            /**
                             * 释放锁，使当前线程进入阻塞状态
                             * 阻塞状态的线程，被interrupted中断后，会抛出异常
                             */
                            mutex.wait();
                        }
                    } catch (InterruptedException e) {
                        // 如果线程在wait之前已经被interrupted，或者在wait中被interrupted，则wait时会抛出此异常，此时退出线程
                        LogUtil.d(TAG, "wait之前或之中线程已被中止，阻塞的wait方法抛出InterruptedException异常，退出后台线程");
                        return;
                    }
                    LogUtil.d(TAG, "消费者线程阻塞等待后 被唤醒，再次取队头元素");
                    source = sourceQueue.dequeue();
                }

                if (source == null) {
                    // 防御代码，防止生产者 生产出null
                    LogUtil.d(TAG, "防御代码，生产者产生了一个null");
                    continue;
                }

                // 从队头开始出队数据，取到一个就立即上传，出队操作会清除等待队列中的记录
                if (source.type == DaemonSource.TYPE_IMAGE) {
                    try {
                        LogUtil.d(TAG, "开始图片上传，" + source.localPath);
                        // TODO 执行上传图片的代码
                        String uploadImgUrl = "";
                        successMap.put(source.localPath, DaemonResult.image(uploadImgUrl));
                        LogUtil.d(TAG, "图片上传成功，" + source.localPath);
                    } catch (Exception e) {
                        /**
                         * 这个 异常 并不能 明确表述异常的原因，因BaseApi直接将有些异常转义为 HttpEx或InternalEx
                         * 正确的做法:在这里捕获到 InterruptedIOException 后，return退出线程
                         */
                        LogUtil.d(TAG, "图片上传失败，" + source.localPath + "," + e.getMessage());
                    }
                } else if (source.type == DaemonSource.TYPE_VIDEO) {
                    try {
                        LogUtil.d(TAG, "开始视频上传，" + source.localPath);
                        // TODO 执行上传视频的代码
                        String uploadVideoUrl = "";
                        successMap.put(source.localPath, DaemonResult
                                .video(uploadVideoUrl, "", 0, 0));
                        LogUtil.d(TAG, "视频上传成功，" + source.localPath);

                    } catch (Exception e) {
                        /**
                         * 这个 异常 并不能 明确表述异常的原因，因BaseApi直接将有些异常转义为 HttpEx或InternalEx
                         * 正确的做法:在这里捕获到 InterruptedIOException 后，return退出线程
                         */
                        LogUtil.d(TAG, "视频上传失败，" + source.localPath + "," + e.getMessage());
                    }
                }
            }
        }
    };

    @MainThread
    public @Nullable
    String getImageNetUrl(String localImagePath) {
        DaemonResult daemonResult = successMap.get(localImagePath);
        String netUrl = daemonResult.netUrl;
        if (StringUtil.isEmpty(netUrl)) {
            return localImagePath;
        }
        return netUrl;
    }

    @MainThread
    public @Nullable
    DaemonResult getVideoResult(String localVideoPath) {
        DaemonResult daemonResult = successMap.get(localVideoPath);
        return daemonResult;
    }

}
