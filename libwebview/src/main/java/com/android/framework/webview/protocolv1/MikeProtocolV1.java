package com.android.framework.webview.protocolv1;

import android.net.Uri;
import android.util.Log;

import com.android.framework.webview.IMikeProtocol;

/**
 * Created by meikai on 16/5/15.
 */
public class MikeProtocolV1 implements IMikeProtocol {

    private String host;
    private String path;
    private String params;

    private static IMikeProtocolV1Helper mikeProtocolV1Helper;

    public static void setIMikeProtocolV1Helper(IMikeProtocolV1Helper IMikeProtocolV1Helper) {
        mikeProtocolV1Helper = IMikeProtocolV1Helper;
    }

    @Override
    public String handleProtocol(Uri uri) {

        host = uri.getHost();
        path = uri.getPath();

        String result = "";

        Log.e("MikeProtocolV1", "uri=" + uri.toString() + "    ,host=" + host + "    , path=" + path);

        if ("system".equals(host)) {
            if ("/version".equals(path)) {
                result = mikeProtocolV1Helper.version();
            } else if ("/toast".equals(path)) {
                params = uri.getQueryParameter("message");
                mikeProtocolV1Helper.toast(params);
            } else if ("/log".equals(path)) {
                params = uri.getQueryParameter("message");
                mikeProtocolV1Helper.log(params);
            }
        }

        return result;
    }


}
