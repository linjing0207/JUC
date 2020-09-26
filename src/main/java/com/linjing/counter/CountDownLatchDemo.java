package com.linjing.counter;

import java.util.concurrent.CountDownLatch;

//计数器
public class CountDownLatchDemo {
    public static void main(String[] args) throws InterruptedException {
        //倒计时, 总数是6, 必须要执行任务的时候, 再使用!
        CountDownLatch cdl = new CountDownLatch(6);
        //循环6次, 让6个线程都跑一遍
        for (int i = 1; i <= 6; i++) {
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName() + "当前线程结束了");
                cdl.countDown(); // -1
            }, String.valueOf(i)).start();
        }
        cdl.await(); //等待计数器归零, 然后再向下执行

        System.out.println("全部结束啦!");
    }
}
