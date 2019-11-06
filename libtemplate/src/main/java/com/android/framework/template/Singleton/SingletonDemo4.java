package com.android.framework.template.Singleton;

/**
 * 枚举类（线程安全，调用效率高，不能延时加载，可以天然的防止反射和反序列化调用）
 * Created by meikai on 2019/10/22.
 */
public enum SingletonDemo4 {

    //枚举元素本身就是单例
    INSTANCE;

    //添加自己需要的操作
    public void singletonOperation() {
    }
}