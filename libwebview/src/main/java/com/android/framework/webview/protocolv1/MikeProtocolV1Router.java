package com.android.framework.webview.protocolv1;

import android.net.Uri;
import android.util.Log;

import com.android.framework.webview.IMikeProtocol;

/**
 * Created by meikai on 16/5/15.
 */
public class MikeProtocolV1Router implements IMikeProtocol {

    private String host;
    private String path;
    private String params;

    private static IMikeProtocolV1Processor mikeProtocolV1Processor;

    public static void setIMikeProtocolV1Helper(IMikeProtocolV1Processor IMikeProtocolV1Helper) {
        mikeProtocolV1Processor = IMikeProtocolV1Helper;
    }

    @Override
    public String handleProtocol(Uri uri) {

        host = uri.getHost();
        path = uri.getPath();

        String result = "";

        Log.e("MikeProtocolV1", "uri=" + uri.toString() + "    ,host=" + host + "    , path=" + path);

        if ("system".equals(host)) {
            if ("/version".equals(path)) {
                result = mikeProtocolV1Processor.version();
            } else if ("/toast".equals(path)) {
                params = uri.getQueryParameter("message");
                mikeProtocolV1Processor.toast(params);
            } else if ("/log".equals(path)) {
                params = uri.getQueryParameter("message");
                mikeProtocolV1Processor.log(params);
            }
        }

        if ("jump".equals(host)) {
            if ("/action".equals(path)) {
                params = uri.getQueryParameter("message");
                mikeProtocolV1Processor.action(params);
            }
        }

        return result;
    }


}
