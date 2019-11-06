package com.android.framework.template.Singleton;

/**
 * Double CheckLock实现单例：DCL也就是双重锁判断机制，写法太复杂
 * Created by meikai on 2019/10/22.
 */
public class SingletonDemo5 {

    private volatile static SingletonDemo5 SingletonDemo5;

    private SingletonDemo5() {
    }

    public static SingletonDemo5 newInstance() {
        if (SingletonDemo5 == null) {
            synchronized (SingletonDemo5.class) {
                if (SingletonDemo5 == null) {
                    SingletonDemo5 = new SingletonDemo5();
                }
            }
        }
        return SingletonDemo5;
    }
}
