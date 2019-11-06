package com.android.framework.template.Singleton;

/**
 * 饿汉式(线程安全，调用效率高，但是不能延时加载)：
 * Created by meikai on 2019/10/22.
 */
public class SingletonDemo1 {
    private static SingletonDemo1 instance = new SingletonDemo1();

    private SingletonDemo1() {
    }

    public static SingletonDemo1 getInstance() {
        return instance;
    }
}
