package com.android.framework.demo.activity.daemon;

/**
 * 后台上传的结果信息
 * Created by meikai on 2018/06/01.
 */
public class DaemonResult {


    public @DaemonSource.SOURCE_TYPE
    int type;

    /**
     * 上传成功后的 图片或视频 的 服务器路径
     */
    public String netUrl;

    public String videoCoverNetUrl;
    public int videoCoverWidth;
    public int videoCoverHeight;

    public static DaemonResult image(String netUrl) {
        DaemonResult result = new DaemonResult();
        result.netUrl = netUrl;
        result.type = DaemonSource.TYPE_IMAGE;
        return result;
    }

    public static DaemonResult video(String netUrl, String coverNetUrl, int coverWidth, int coverHeight) {
        DaemonResult result = new DaemonResult();
        result.netUrl = netUrl;
        result.videoCoverNetUrl = coverNetUrl;
        result.videoCoverWidth = coverWidth;
        result.videoCoverHeight = coverHeight;
        result.type = DaemonSource.TYPE_VIDEO;
        return result;
    }

}
