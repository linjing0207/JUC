package com.linjing.pc;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * A执行完调用B, B执行完调用C, C执行完调用A
 */
public class LockCondition02 {
    public static void main(String[] args) {
        Data3 data3 = new Data3();
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                data3.printA();
            }
        }, "A").start();
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                data3.printB();
            }
        }, "B").start();
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                data3.printC();
            }
        }, "C").start();
    }
}


//等待, 业务, 通知
class Data3 {

    private int number = 1; //1:A 2:B 3:C

    private Lock lock = new ReentrantLock();
    //同步监视器
    private Condition condition1 = lock.newCondition();
    private Condition condition2 = lock.newCondition();
    private Condition condition3 = lock.newCondition();

    public void printA() {
        lock.lock();
        try {
            //业务, 判断 -> 执行 -> 通知
            while (number != 1) {
                //等待
                condition1.await();
            }
            System.out.println(Thread.currentThread().getName() + ": AAAAA");
            //精准唤醒指定的人,B
            number = 2;
            condition2.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void printB() {
        lock.lock();
        try {
            //业务, 判断 -> 执行 -> 通知
            while (number != 2) {
                //等待
                condition2.await();
            }
            System.out.println(Thread.currentThread().getName() + ": BBBBBB");
            //精准唤醒指定的人,B
            number = 3;
            condition3.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void printC() {
        lock.lock();
        try {
            //业务, 判断 -> 执行 -> 通知
            while (number != 3) {
                //等待
                condition3.await();
            }
            System.out.println(Thread.currentThread().getName() + ": CCCC");
            //精准唤醒指定的人,B
            number = 1;
            condition1.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    //生产线: 下单->支付->交易->物流..(仅思路)
}
