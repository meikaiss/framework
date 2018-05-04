package com.android.framework.demo;

import com.facebook.stetho.Stetho;

/**
 * Created by meikai on 15/10/28.
 */
public class DebugDemoApplication extends DemoApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
    }
}
