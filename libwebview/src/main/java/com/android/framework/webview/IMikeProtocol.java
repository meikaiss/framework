package com.android.framework.webview;

/**
 * Created by meikai on 16/5/15.
 */
public interface IMikeProtocol {

    IMikeProtocol registerProtocolAnt(IMikeProtocolAnt iMikeProtocolAnt);

    String handleProtocol(String url);
}
