package com.android.framework.demo.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

/**
 * Created by meikai on 16/1/17.
 */
public class NotificationService extends Service {

    @Override
    public void onCreate() {
        super.onCreate();


    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        int param = intent.getIntExtra("param", -1);
        Toast.makeText(this, "点击了通知上的按钮"+param, Toast.LENGTH_SHORT).show();
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {


        return null;
    }
}
