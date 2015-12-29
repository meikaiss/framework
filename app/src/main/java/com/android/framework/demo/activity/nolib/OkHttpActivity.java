package com.android.framework.demo.activity.nolib;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

/**
 * Created by meikai on 15/12/29.
 */
public class OkHttpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        new Thread(new Runnable() {
            @Override
            public void run() {

                OkHttpClient okHttpClient = new OkHttpClient();

                Request request = new Request.Builder().url("http://www.weather.com.cn/adat/sk/101010300.html").build();

                Call call = okHttpClient.newCall(request);

                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(Request request, IOException e) {

                    }

                    @Override
                    public void onResponse(Response response) throws IOException {

                        String result = new String(response.body().bytes());
                        Log.e("onResponse", result);
//                        Toast.makeText(OkHttpActivity.this, response.toString(), Toast.LENGTH_SHORT).show();

                    }
                });

            }
        }).start();

    }


}
