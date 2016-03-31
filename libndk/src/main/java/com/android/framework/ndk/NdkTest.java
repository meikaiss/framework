package com.android.framework.ndk;

/**
 * Created by meikai on 16/3/30.
 */
public class NdkTest {
    static {
        System.loadLibrary("ndk");
    }

    public static native String getStringInNDK();


    public static native String encryptString(String name);

}
