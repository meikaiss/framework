package com.androidx.codetemplete.singleInstance;

/**
 * 饿汉单例模式
 *
 * 声明静态时已经初始化，在获取对象之前就初始化
 *
 * 优点：获取对象的速度快，线程安全（因为虚拟机保证只会装载一次，在装载类的时候是不会发生并发的）
 * 缺点：耗内存（若类中有静态方法，在调用静态方法的时候类就会被加载，类加载的时候就完成了单例的初始化，拖慢速度）
 * Created by meikai on 2019/08/08.
 */
public class HungrySingleton {

    //在类加载时就完成了初始化，所以类加载较慢，但获取对象的速度快
    private static HungrySingleton instance = new HungrySingleton();//静态私有成员，已初始化

    private HungrySingleton() {
        //私有构造函数
    }

    //静态，不用同步（类加载时已初始化，不会有多线程的问题）
    public static HungrySingleton getInstance() {
        return instance;
    }

}

