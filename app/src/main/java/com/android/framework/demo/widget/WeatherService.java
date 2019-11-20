package com.android.framework.demo.widget;

import android.app.PendingIntent;
import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.android.framework.demo.R;
import com.android.framework.demo.data.Weather;
import com.android.framework.api.ApiResponse;
import com.android.framework.api.OkHttpManager;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.Request;

/**
 * Created by meikai on 16/1/12.
 */
public class WeatherService extends Service {

    private static String refreshTime;

    Timer timer;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {

                String url = "http://www.weather.com.cn/adat/sk/101200101.html";
                OkHttpManager.getInstance().httpGetAsync(url, new OkHttpManager.ApiCallBack() {
                    @Override
                    public void onApiSuccess(ApiResponse apiResponse) {

                        try {
                            Weather weather = apiResponse.getData(Weather.class);

                            refreshTime = new SimpleDateFormat("HH:mm:ss").format(new Date());

                            updateWidget(weather.weatherinfo.city + weather.weatherinfo.WD + weather.weatherinfo.WS);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onApiFailed(Request request, IOException e) {

                        Toast.makeText(WeatherService.this, "请求失败", Toast.LENGTH_SHORT).show();

                    }
                });
            }
        }, 0, 2000);

    }

    private void updateWidget(String weatherInfo) {

        RemoteViews rv = new RemoteViews(getPackageName(), R.layout.weather_app_widget);
        rv.setTextViewText(R.id.appwidget_text, weatherInfo);
        rv.setTextViewText(R.id.tv_refresh_time, "最后刷新时间:" + refreshTime);

        Intent intent = new Intent();
        intent.setClass(getApplicationContext(), WeatherService.class);

        PendingIntent pendingIntent = PendingIntent.getService(this, 0, intent, 0);
        rv.setOnClickPendingIntent(R.id.btn_refresh, pendingIntent);

        AppWidgetManager awm = AppWidgetManager.getInstance(getApplicationContext());
        ComponentName cn = new ComponentName(getApplicationContext(), WeatherAppWidgetProvider.class);

        awm.updateAppWidget(cn, rv);
    }
}
