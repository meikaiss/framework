package com.android.framework.webview;

/**
 * Created by meikai on 16/5/15.
 */
public abstract class IMikeProtocolAnt {

    private String host;
    private String path;

    public IMikeProtocolAnt(String host) {
        this.host = host;
    }

    private boolean canHandler(String url){
        return url.startsWith(host);
    }


    public void tryHandler(String url){
        if (!canHandler(url))
            return;

        handler(url);
    }

    public abstract void handler(String url);


}
