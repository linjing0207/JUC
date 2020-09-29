package com.linjing.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

//可重入锁, 递归锁
//lock
public class Demo02 {
    public static void main(String[] args) {
        Phone2 phone = new Phone2();
        new Thread(() -> {
            phone.sendMsg();
        }, "A").start();
        new Thread(() -> {
            phone.call();
        }, "B").start();
    }
}


class Phone2 {
    Lock lock = new ReentrantLock();

    //外面一把锁
    public void sendMsg() {
        lock.lock(); //细节问题: lock锁的加锁解锁必须配对.
        try {
            System.out.println(Thread.currentThread().getName() + ": send message.");
            call(); //里面一把锁
            //Lock: 外面的lock和call()里的lock是不同的锁
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void call() {
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + ": call.");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}
