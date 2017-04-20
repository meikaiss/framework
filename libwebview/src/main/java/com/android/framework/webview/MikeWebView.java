package com.android.framework.webview;

import android.annotation.TargetApi;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.android.framework.webview.protocolv1.MikeProtocolV1Router;

/**
 * Created by meikai on 16/5/15.
 */
public class MikeWebView extends WebView {


    private boolean online = true;

    private MikeWebViewInterface mikeWebViewInterface;
    private IMikeProtocol mikeProtocol;


    public MikeWebView(Context context) {
        super(context);
    }

    public MikeWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MikeWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public MikeWebView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


    public void init() {

        this.getSettings().setJavaScriptEnabled(true);
        this.setWebViewClient(new WebViewClient());

        this.mikeProtocol = new MikeProtocolV1Router();

        this.mikeWebViewInterface = new MikeWebViewInterface();

        addJavascriptInterface(mikeWebViewInterface, "mikeAndroidWebViewInterface");
    }

    class MikeWebViewInterface {

        @JavascriptInterface
        public String getMikeWebViewData(String url, final String callback) {

            Log.e("getMikeWebViewData", url + "_" + callback);

            Uri uri = Uri.parse(url);
            mikeProtocol.handleProtocol(uri);

            return "test1";
        }

        @JavascriptInterface
        public String getMikeWebViewData(String url) {

            Log.e("getMikeWebViewData", url);


            Uri uri = Uri.parse(url);

            return mikeProtocol.handleProtocol(uri);
        }
    }


    private void convulsions() {
        this.post(new Runnable() {
            @Override
            public void run() {
                MikeWebView.this.setNetworkAvailable(online = !online);
            }
        });
    }

}
