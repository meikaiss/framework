package com.android.framework.demo.activity.nolib;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.framework.demo.R;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by meikai on 15/10/28.
 */
public class HandlerThreadActivity extends Activity {

    Handler handler;
    Handler threadHandler;

    ListView listView;
    List<String> msgList = new ArrayList<>();
    ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handler_thread);
        listView = (ListView) findViewById(R.id.list_view);

        arrayAdapter = new ArrayAdapter<String>(this, R.layout.item_demo, R.id.tv_item_name, msgList);
        listView.setAdapter(arrayAdapter);


        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Looper.prepare();

                handler = new Handler(){
                    @Override
                    public void handleMessage(Message msg) {
                        super.handleMessage(msg);

                        msgList.add("非主线程Thread接收到消息 ＝ " + msg.obj.toString());
                        arrayAdapter.notifyDataSetChanged();

                        Log.e("handlerMsg", "非主线程Thread接收到消息 ＝ " + msg.obj.toString());
                    }
                };

                // To Do
                Looper.loop();
            }
        });
        thread.start();

        HandlerThread handlerThread = new HandlerThread("myHandlerThread", Process.THREAD_PRIORITY_DEFAULT){
            @Override
            public void run() {
                super.run();

                // To Do
            }
        };
        handlerThread.start();

        threadHandler = new MyHandler(this, handlerThread.getLooper());

    }

    private static class MyHandler extends Handler{

        private WeakReference<HandlerThreadActivity> activity;

        public MyHandler(HandlerThreadActivity activity, Looper looper) {
            super(looper);
            this.activity = new WeakReference<HandlerThreadActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            activity.get().msgList.add("非主线程HandlerThread接收到消息 ＝ " + msg.obj.toString());
            activity.get().arrayAdapter.notifyDataSetChanged();

            Log.e("handlerMsg", "非主线程HandlerThread接收到消息 ＝ " + msg.obj.toString());
        }
    }

    public void sendMsg(View view){

        Message msg = handler.obtainMessage();
        msg.obj = "开始发送消息到非主线程Thread";
        msgList.add(msg.obj.toString());
        arrayAdapter.notifyDataSetChanged();

        handler.sendMessage(msg);

    }

    public void sendMsgHandlerThread(View view){

        Message msg = threadHandler.obtainMessage();
        msg.obj = "开始发送消息到非主线程HandlerThread";
        msgList.add(msg.obj.toString());
        arrayAdapter.notifyDataSetChanged();

        threadHandler.sendMessage(msg);

    }

}
