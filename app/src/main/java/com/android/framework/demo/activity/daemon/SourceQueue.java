package com.android.framework.demo.activity.daemon;

import android.support.annotation.MainThread;
import android.support.annotation.WorkerThread;

import com.android.framework.util.CollectionUtil;

import java.util.LinkedList;


/**
 * 生产者 所生产出 的数据源队列
 * Created by meikai on 2018/06/03.
 */
public class SourceQueue {

    private LinkedList<DaemonSource> waitList = new LinkedList<>();

    /**
     * 通过同步锁信号量，保证多线程安全
     */
    final Object mutex;

    public SourceQueue() {
        this.mutex = new Object();
    }

    /**
     * 入队:向队尾插入一条数据
     */
    @MainThread
    public void enqueue(DaemonSource daemonSource) {
        synchronized (mutex) {
            waitList.addLast(daemonSource);
        }
    }

    /**
     * 出队:取出队头的元素
     */
    @WorkerThread
    public DaemonSource dequeue() {
        DaemonSource result;
        synchronized (mutex) {
            if (waitList.size() > 0) {
                result = waitList.removeFirst();
            } else {
                result = null;
            }
        }
        return result;
    }

    /**
     * 判断当前队列中是否存在这条本地路径的记录
     *
     * @return true:存在;  false:不存在
     */
    public boolean contains(DaemonSource daemonSource) {
        synchronized (mutex) {
            if (daemonSource == null || CollectionUtil.isEmpty(waitList)) {
                return false;
            }

            return waitList.contains(daemonSource);
        }
    }

    public void clear() {
        synchronized (mutex) {
            waitList.clear();
        }
    }

    public int size() {
        synchronized (mutex) {
            return waitList.size();
        }
    }

}
