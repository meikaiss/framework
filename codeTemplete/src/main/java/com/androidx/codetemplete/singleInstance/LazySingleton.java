package com.androidx.codetemplete.singleInstance;

/**
 * Created by meikai on 2019/08/23.
 */
public class LazySingleton {

    //类初始化时，不初始化这个对象(延时加载，真正用的时候再创建)
    private static LazySingleton instance;

    //构造器私有化
    private LazySingleton() {
    }

    //方法同步，调用效率低
    public static synchronized LazySingleton getInstance() {
        if (instance == null) {
            instance = new LazySingleton();
        }
        return instance;
    }
}
