package com.android.framework.api;

import android.os.Handler;
import android.os.Looper;

import com.alibaba.fastjson.JSONObject;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Created by meikai on 16/1/5.
 */
public class OkHttpManager {

    private static final String DEFAULT_CODE_FORMAT = "utf-8";

    private static Handler handler;

    private static OkHttpManager instance;

    private static OkHttpClient okHttpClient;

    public static OkHttpManager getInstance() {
        if (instance == null) {
            synchronized (OkHttpManager.class) {
                instance = new OkHttpManager();
                okHttpClient = new OkHttpClient();

                okHttpClient.setConnectTimeout(10, TimeUnit.SECONDS);

                handler = new Handler(Looper.getMainLooper());
            }
        }
        return instance;
    }

    public Response httpGetSync(String url) throws IOException {
        Request request = new Request.Builder().url(url).build();
        Call call = okHttpClient.newCall(request);

        Response response = call.execute();

        if(response.isSuccessful())
            return response;
        else
            return null;
    }

    public void httpGetAsync(String url, final ApiCallBack apiCallBack) {

        final Request request = new Request.Builder().url(url).build();
        Call call = okHttpClient.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                sendFailed2UiThread(request, e, apiCallBack);
            }

            @Override
            public void onResponse(Response response) throws IOException {
                final String result = new String(response.body().bytes(), DEFAULT_CODE_FORMAT);

                JSONObject data = JSONObject.parseObject(result);

                JSONObject responseJsonObj = new JSONObject();
                responseJsonObj.put("data", data);
                responseJsonObj.put("success", true);
                responseJsonObj.put("responseCode", 0);
                responseJsonObj.put("message", null);

                ApiResponse apiResponse = new ApiResponse(responseJsonObj);

                sendSuccess2UiThread(apiResponse, apiCallBack);
            }
        });

    }


    private void sendSuccess2UiThread(final ApiResponse result, final ApiCallBack apiCallBack) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                apiCallBack.onApiSuccess(result);
            }
        });
    }

    private void sendFailed2UiThread(final Request request, final IOException e, final ApiCallBack apiCallBack) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                apiCallBack.onApiFailed(request, e);
            }
        });
    }


    public interface ApiCallBack {

        void onApiSuccess(ApiResponse apiResponse);

        void onApiFailed(Request request, IOException e);

    }

}
