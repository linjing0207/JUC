package com.linjing.tvolatile;

import java.util.concurrent.TimeUnit;

public class JMMDemo01 {
    //volatile保证可见性
    private volatile static int num = 0;

    public static void main(String[] args) {
        fun1();
    }

    //1. volatile保证可见性
    public static void fun1() {
        new Thread(() -> { //该线程对主内存的num变化不知道
            while (num == 0) {
                //如果不加volatile, 这个线程会一直跑; 程序停不下来, 造成死循环.
                //使用volatile, 可以保证可见性, 会跳出这个循环.
            }
        }).start();
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        num = 1;
        System.out.println(num);
    }



}
