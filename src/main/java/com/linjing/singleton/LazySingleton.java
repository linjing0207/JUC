package com.linjing.singleton;

//单例模式的懒汉模式实现
public class LazySingleton {

    //私有静态成员
    private volatile static LazySingleton singleton;

    //双检锁/双重校验锁（DCL，即 double-checked locking）
    public static LazySingleton getInstance() {
        //第一层校验: 提高效率，避免不必要的同步；如果实例已经创建，就不必等锁了。
        if (singleton == null) {
            //并发: 多线程
            synchronized (LazySingleton.class) {
                //第二层校验: 若没有第二层校验线程B在等锁的过程中，其实线程A已经创建好实例了，线程B进入同步方法，又重新创建了实例
                if (singleton == null) {
                    //new LazySingleton() 不是原子操作, 所以使用volatile禁止指令重排
                    singleton = new LazySingleton();
                    /**
                     * 1. 分配内存空间 memory = allocate();
                     * 2. 执行构造方法, 初始化对象 ctorInstance(memory);
                     * 3. 把这个对象指向这个空间 instance = memory;
                     *
                     * JVM的指令重排：132
                     * 若线程B执行了1, 3, 还未执行2. 线程A在第一层校验的时候，获得的instance已经非空了，则返回instance。
                     * 而此时的instance还没有初始化对象，只是一个虚无的地址。
                     * 使用了volatile关键字, 可以避免指令重排。
                     */
                }
            }
        }
        return singleton;
    }
}
