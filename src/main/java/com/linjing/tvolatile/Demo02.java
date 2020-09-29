package com.linjing.tvolatile;

import java.util.concurrent.atomic.AtomicInteger;

public class Demo02 {
    //private volatile static int num = 0;
    private static AtomicInteger num = new AtomicInteger(0);

    public static void main(String[] args) {
        fun2();
    }

    //2. volatile不保证原子性
    //原子类的Integer可以保证原子性
    public static void fun2() {
        //理论上, num = 20000
        //加上volatile关键字, num = 19991
        for (int i = 0; i < 20; i++) {
            new Thread(() -> {
                for (int j = 0; j < 1000; j++) {
                    add();
                }
            }).start();
        }
        while (Thread.activeCount() > 2) { //main, gc
            Thread.yield();
        }

        System.out.println(Thread.currentThread().getName() + " " + num);
    }

    //加上synchronized, 可以保证原子性.
    public static void add() {
        //num++; //不是原子操作
        /**
         * 1. 获得这个值
         * 2. +1
         * 3. 返回这个值
         */
        num.getAndIncrement(); //改成AtomicInteger 原子类型
    }
}
