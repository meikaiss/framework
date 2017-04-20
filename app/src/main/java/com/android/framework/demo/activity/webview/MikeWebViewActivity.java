package com.android.framework.demo.activity.webview;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.android.framework.demo.R;
import com.android.framework.webview.MikeWebView;
import com.android.framework.webview.protocolv1.IMikeProtocolV1Processor;
import com.android.framework.webview.protocolv1.MikeProtocolV1Router;

/**
 * Created by meikai on 16/5/15.
 */
public class MikeWebViewActivity extends AppCompatActivity {

    MikeWebView mikeWebView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mike_webview);

        findViewById(R.id.imgb_back).setOnClickListener(v -> MikeWebViewActivity.this.onBackPressed());

        MikeProtocolV1Router.setIMikeProtocolV1Helper(new IMikeProtocolV1Processor() {
            @Override
            public String version() {
                return "型号" + android.os.Build.MODEL + ", 安卓版本" + android.os.Build.VERSION.RELEASE;
            }

            @Override
            public void toast(String message) {
                Toast.makeText(MikeWebViewActivity.this, message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void log(String message) {
                Log.e("MikeProtocolV1", "message = " + message);
            }

            @Override
            public void action(String message) {
                Intent intent = new Intent(message);
                startActivity(intent);
            }
        });

        mikeWebView = (MikeWebView) findViewById(R.id.mike_web_view);
        mikeWebView.init();

        mikeWebView.loadUrl("file:///android_asset/webview/demo/v1.0.html");
//        mikeWebView.loadUrl("file:///android_asset/page/meikai.html");

//        mikeWebView.loadUrl("http://www.jiecao.fm/news/article/detailV3/XjY1MDjO.htm?u=k5yZx0eP&down=true&v=3.9.8");


    }
}
