package com.androidx.codetemplete.singleInstance;

/**
 * 枚举实现的 单例
 * 线程安全，调用效率高，不能延时加载，可以天然的防止反射和反序列化调用
 * Created by meikai on 2019/08/23.
 */
public enum EnumSingleton {

    //枚举元素本身就是单例
    INSTANCE;

    //添加自己需要的操作
    public void singletonOperation() {
    }

}
