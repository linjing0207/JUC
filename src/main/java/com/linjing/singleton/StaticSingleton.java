package com.linjing.singleton;

//单例模式的静态内部类实现
//外部类Holder
public class StaticSingleton {
    //私有构造器
    private StaticSingleton() {

    }

    //只有通过显式调用getInstance方法时，才会显式装载 InnerClass 类，从而实例化 instance
    public static StaticSingleton getInstance() {
        return SingletonHolder.holder;
    }

    //静态内部类
    public static class SingletonHolder {
        private static final StaticSingleton holder = new StaticSingleton();
    }
    /**
     * 静态内部类不会随着外部类的加载而加载 ,只有静态内部类的静态成员被调用时才会进行加载,
     * 这样既保证的惰性初始化（Lazy-Initialization），又由JVM保证了多线程并发访问的正确性。
     */
}
