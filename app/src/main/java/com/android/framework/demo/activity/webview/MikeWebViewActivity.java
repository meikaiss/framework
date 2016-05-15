package com.android.framework.demo.activity.webview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.android.framework.demo.R;
import com.android.framework.webview.MikeWebView;

/**
 * Created by meikai on 16/5/15.
 */
public class MikeWebViewActivity extends AppCompatActivity {

    MikeWebView mikeWebView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mike_webview);

        mikeWebView = (MikeWebView) findViewById(R.id.mike_web_view);
        mikeWebView.init();

        mikeWebView.loadUrl("file:///android_asset/webview/demo/v1.0.html");
//        mikeWebView.loadUrl("http://www.jiecao.fm/news/article/detailV3/XjY1MDjO.htm?u=k5yZx0eP&down=true&v=3.9.8");
    }
}
