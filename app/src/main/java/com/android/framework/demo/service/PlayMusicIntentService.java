package com.android.framework.demo.service;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.android.framework.demo.DemoApplicaton;

/**
 * Created by meikai on 15/10/28.
 */
public class PlayMusicIntentService extends IntentService {

    private static final String TAG = PlayMusicIntentService.class.getSimpleName();


    public PlayMusicIntentService() {
        super(TAG);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e("onCreate", "onCreate");
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        Log.e("onStart", "onStart");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.e("onHandleIntent", "onHandleIntent, 参数=" + intent.getStringExtra("userName"));

        Toast.makeText(DemoApplicaton.getInstance(), "正在启动IntentService", Toast.LENGTH_SHORT).show();

        try {
            Thread.sleep(1000*5);
            Log.e("onHandleIntent", "线程休息完毕");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
