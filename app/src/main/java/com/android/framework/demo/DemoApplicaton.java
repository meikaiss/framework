package com.android.framework.demo;

import android.app.Application;

/**
 * Created by meikai on 15/10/28.
 */
public class DemoApplicaton extends Application {

    private static DemoApplicaton instance;

    public static DemoApplicaton getInstance(){
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }
}
