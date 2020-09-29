package com.linjing.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

//自旋锁, CAS
public class SpinLockDemo {
    //int 0
    //Thread null(默认)
    AtomicReference<Thread> atomicReference = new AtomicReference<>();

    //加锁
    public void lock() {
        Thread thread = Thread.currentThread();
        //自旋锁
        while (!atomicReference.compareAndSet(null, thread)) { //如果是null, 就用thread替换

        }
        System.out.println(thread.getName() + "==> myLock加锁");
    }

    //解锁
    public void unLock() {
        Thread thread = Thread.currentThread();
        atomicReference.compareAndSet(thread, null);
        System.out.println(thread.getName() + "==> myUnLock解锁");
    }
}


class Test {
    public static void main(String[] args) {
        //自己写的自旋锁, 底层使用CAS实现
        SpinLockDemo lock = new SpinLockDemo();
        new Thread(() -> {
            lock.lock();
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unLock();
            }
        }, "T1").start();
        new Thread(() -> {
            lock.lock();
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unLock();
            }
        }, "T2").start();
    }
}
