package com.android.framework.demo;

import android.app.Application;
import android.util.Log;

import java.util.concurrent.TimeoutException;

/**
 * Created by meikai on 15/10/28.
 */
public class DemoApplication extends Application {

    private static DemoApplication instance;

    public static DemoApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        Log.e("DemoApplication", "DemoApplication_onCreate");

        Thread.UncaughtExceptionHandler uncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                if (t.getName().equals("FinalizerWatchdogDaemon")
                        && e instanceof TimeoutException) {
                    // 无视此异常
                } else {
                    uncaughtExceptionHandler.uncaughtException(t, e);
                }
            }
        });
    }
}
