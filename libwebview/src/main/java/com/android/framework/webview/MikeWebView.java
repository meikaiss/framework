package com.android.framework.webview;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebResourceRequest;
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
        this.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return super.shouldOverrideUrlLoading(view, request);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return super.shouldOverrideUrlLoading(view, url);
            }
        });

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
