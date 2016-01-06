package com.android.framework.demo.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.android.framework.demo.R;
import com.android.framework.demo.data.Weather;
import com.android.framework.libapi.ApiResponse;
import com.android.framework.libapi.OkHttpManager;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

/**
 * Created by meikai on 15/12/29.
 */
public class OkHttpActivity extends AppCompatActivity implements View.OnClickListener {


    TextView tvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_okhttp);

        tvResult = (TextView) findViewById(R.id.tv_result);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btn_async:

                String url = "http://www.weather.com.cn/adat/sk/101200101.html";
                OkHttpManager.getInstance().httpGetAsync(url, new OkHttpManager.ApiCallBack() {
                    @Override
                    public void onApiSuccess(ApiResponse apiResponse) {

                        try {
                            Weather weather = apiResponse.getData(Weather.class);
                            tvResult.setText(weather.weatherinfo.city + weather.weatherinfo.WD
                                    +weather.weatherinfo.WS);
                        }catch (Exception e){
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onApiFailed(Request request, IOException e) {

                        tvResult.setText("请求失败=" + request.url() + "\n" + e.getMessage());

                    }
                });
                break;
            case R.id.btn_sync:

                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        String url = "http://www.weather.com.cn/adat/sk/101200101.html";
                        try {
                            Response response = OkHttpManager.getInstance().httpGetSync(url);

                            final String result = new String(response.body().bytes());

                            new Handler(Looper.getMainLooper()).post(new Runnable() {
                                @Override
                                public void run() {
                                    tvResult.setText(result);
                                }
                            });
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                }).start();
                break;
        }

    }

}
