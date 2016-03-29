package com.android.framework.demo;

import android.app.Application;
import android.util.Log;

/**
 * Created by meikai on 15/10/28.
 */
public class DemoApplication extends Application {

    private static DemoApplication instance;

    public static DemoApplication getInstance(){
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        Log.e("DemoApplication", "DemoApplication_onCreate");
    }
}
