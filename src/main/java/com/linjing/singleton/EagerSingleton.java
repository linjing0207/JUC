package com.linjing.singleton;

//单例模式的饿汉模式
public class EagerSingleton {
    //可能会浪费空间, 内存浪费.

    private final static EagerSingleton singleton = new EagerSingleton();

    //构造器私有
    public EagerSingleton() {
    }

    public static EagerSingleton getInstance() {
        return singleton;
    }
}
