package com.androidx.codetemplete.singleInstance;

/**
 * 静态内部类 实现的 单例
 *
 * 注：线程安全，调用效率高，可以延时加载
 * 
 * Created by meikai on 2019/08/23.
 */
public class StaticInnerClzSingleton {

    private static class SingletonClassInstance {
        private static final StaticInnerClzSingleton instance = new StaticInnerClzSingleton();
    }

    private StaticInnerClzSingleton() {
    }

    public static StaticInnerClzSingleton getInstance() {
        return SingletonClassInstance.instance;
    }


}
