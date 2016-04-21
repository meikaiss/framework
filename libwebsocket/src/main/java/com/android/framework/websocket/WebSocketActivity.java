package com.android.framework.websocket;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by meikai on 16/4/21.
 */
public class WebSocketActivity extends AppCompatActivity {

    private ListView listView;
    private EditText editText;
    private Button button;

    private ArrayList<ImData> list = new ArrayList<>();
    private MyAdapter adapter;

    WebSocketClient wsc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_websocket);

        listView = (ListView) findViewById(R.id.listV);
        adapter = new MyAdapter(list);
        listView.setAdapter(adapter);

        editText = (EditText) findViewById(R.id.edit_content);
        button = (Button) findViewById(R.id.btn_send);

        try {
            wsc = new WebSocketClient(new URI("http://192.168.2.246:8888")) {

                @Override
                public void onOpen(ServerHandshake arg0) {
                    Log.e("WebSocketClient_onOpen", arg0.toString());
                }

                @Override
                public void onMessage(String arg0) {
                    Log.e("_onMessage", arg0);
                    ImData item = new ImData();
                    item.time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
                    item.content = arg0;
                    list.add(item);

                    handler.sendEmptyMessage(0);
                }

                @Override
                public void onError(Exception arg0) {
                    Message msg = Message.obtain();
                    msg.what = -1;
                    msg.obj = arg0.getMessage();
                    handler.sendMessage(msg);

                    Log.e("WebSocketClient_onError", arg0.getMessage());
                }

                @Override
                public void onClose(int arg0, String arg1, boolean arg2) {

                    Log.e("WebSocketClient_onClose", arg1);
                }
            };

            wsc.connect();


        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                wsc.send(editText.getText().toString());
                editText.setText("");

                InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                im.hideSoftInputFromWindow(getCurrentFocus().getApplicationWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        });
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            if(msg.what == -1){
                Toast.makeText(WebSocketActivity.this, msg.obj.toString(), Toast.LENGTH_SHORT).show();
                return;
            }

            adapter.notifyDataSetChanged();
            listView.setSelection(list.size() - 1);
        }
    };

    private class MyAdapter extends BaseAdapter {

        private ArrayList<ImData> list;

        public MyAdapter(ArrayList<ImData> list) {
            super();
            this.list = list;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int arg0) {
            return list.get(arg0);
        }

        @Override
        public long getItemId(int arg0) {
            return arg0;
        }

        @Override
        public View getView(int arg0, View arg1, ViewGroup arg2) {
            arg1 = LayoutInflater.from(WebSocketActivity.this).inflate(R.layout.item_message, null);

            ((TextView) arg1.findViewById(R.id.tv_time)).setText(list.get(arg0).time);
            ((TextView) arg1.findViewById(R.id.tv_content)).setText(list.get(arg0).content);

            return arg1;
        }

    }

    private class ImData {
        public String time;
        public String content;
    }

}