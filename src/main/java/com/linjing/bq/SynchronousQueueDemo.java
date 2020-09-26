package com.linjing.bq;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;

//同步队列 与其他BlockingQueue不一样, SynchronousQueue不存储元素
//put了一个元素, 必须先take出来, 否则不能再put
public class SynchronousQueueDemo {
    public static void main(String[] args) {
        BlockingQueue<String> blockingQueue = new SynchronousQueue<>();

        //put: 放
        new Thread(() -> {
            try {
                for (int i = 1; i <= 3; i++) {
                    System.out.println(Thread.currentThread().getName() + ": put " + i);
                    blockingQueue.put(String.valueOf(i));
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }, "T1").start();

        //take: 拿
        new Thread(() -> {
            try {
                for (int i = 1; i <= 3; i++) {
                    TimeUnit.SECONDS.sleep(3);
                    System.out.println(Thread.currentThread().getName() + ": take " + blockingQueue.take());
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }, "T2").start();

    }
}
