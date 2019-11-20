package com.android.framework.api;

import android.os.Handler;
import android.os.Looper;

import com.alibaba.fastjson.JSONObject;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

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
                OkHttpClient.Builder builder = new OkHttpClient.Builder();
                builder.connectTimeout(10, TimeUnit.SECONDS);
                okHttpClient = builder.build();


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
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
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

            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                sendFailed2UiThread(request, e, apiCallBack);
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
