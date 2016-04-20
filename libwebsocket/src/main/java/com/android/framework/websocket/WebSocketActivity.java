package com.android.framework.websocket;

import android.content.Intent;

import com.android.framework.base.BaseCompactActivity;

import org.java_websocket.client.WebSocketClient;

/**
 * Created by meikai on 16/4/21.
 */
public class WebSocketActivity extends BaseCompactActivity {

    WebSocketClient client;

    @Override
    public int getContentViewLayoutId() {
        return 0;
    }

    @Override
    public void findViews() {

    }

    @Override
    public void setListeners() {

    }

    @Override
    public void parseBundle(Intent intent) {

    }

    @Override
    public void afterView() {

    }
}
