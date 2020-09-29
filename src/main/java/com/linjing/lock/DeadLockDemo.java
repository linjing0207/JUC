package com.linjing.lock;

import java.util.concurrent.TimeUnit;

//死锁问题
public class DeadLockDemo {
    public static void main(String[] args) {
        String lockA = "lockA";
        String lockB = "lockB";
        new Thread(new MyThread(lockA, lockB), "T1").start();
        new Thread(new MyThread(lockB, lockA), "T2").start();
        // 互相想获得对方的资源
        // Thread-0: lockA try to get lockB
        // Thread-1: lockB try to get lockA
    }
}


class MyThread implements Runnable {

    private final String lockA;
    private final String lockB;

    public MyThread(String lockA, String lockB) {
        this.lockA = lockA;
        this.lockB = lockB;
    }

    @Override
    public void run() {
        synchronized (lockA) {
            System.out.println(Thread.currentThread().getName() + ": " + lockA + " try to get " + lockB);
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (lockB) {
                System.out.println(Thread.currentThread().getName() + ": " + lockB + " try to get " + lockA);
            }
        }
    }
}
