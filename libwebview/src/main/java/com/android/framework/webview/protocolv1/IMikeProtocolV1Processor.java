package com.android.framework.webview.protocolv1;

/**
 * Created by meikai on 16/5/26.
 */
public interface IMikeProtocolV1Processor {

    String version();

    void toast(String message);

    void log(String message);


    void action(String message);
}
