package com.linjing.pc;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockCondition {
    public static void main(String[] args) {
        Data2 data = new Data2();
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                data.increase();
            }
        }, "A").start();
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                data.decrease();
            }
        }, "B").start();
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                data.increase();
            }
        }, "C").start();
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                data.decrease();
            }
        }, "D").start();
    }
}


//等待, 业务, 通知
class Data2 {
    private int number = 0;

    Lock lock = new ReentrantLock();
    //Condition取代了对象监视器方法的使用
    Condition condition = lock.newCondition();

    public void increase() {
        try {
            lock.lock();
            //业务代码
            while (number != 0) { //如果是if, 会产生虚假唤醒问题.
                //等待
                condition.await();
            }
            number++;
            System.out.println(Thread.currentThread().getName() + "=>" + number);
            //通知其他线程, 我+1完毕了
            condition.signalAll();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public synchronized void decrease() {
        try {
            lock.lock();
            while (number == 0) {
                //等待
                condition.await();
            }
            number--;
            //通知其他线程, 我-1完毕了
            System.out.println(Thread.currentThread().getName() + "=>" + number);
            condition.signalAll();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}
