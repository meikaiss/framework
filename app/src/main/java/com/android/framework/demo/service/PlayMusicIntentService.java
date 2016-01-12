package com.android.framework.demo.service;

import android.app.IntentService;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.android.framework.demo.DemoApplication;

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

        Message message = toastHandler.obtainMessage();
        message.obj = "IntentService中的线程接收到消息\n参数=" + intent.getStringExtra("userName");
        toastHandler.sendMessage(message);

        try {
            Thread.sleep(1000*5);

            Message message2 = toastHandler.obtainMessage();
            message2.obj = "线程模拟处理耗时5秒完毕";
            toastHandler.sendMessage(message2);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }


    private Handler toastHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            Toast.makeText(DemoApplication.getInstance(), msg.obj.toString(), Toast.LENGTH_SHORT).show();
        }
    };

}
