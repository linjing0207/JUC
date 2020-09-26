package com.linjing.demo02;

//买票例子


import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 公司中的多线程开发, 降低耦合性
 * 线程就是一个单独的资源类, 没有任何附属的操作
 * 1. 属性, 方法
 */
public class SaleTicketDemo02 {
    public static void main(String[] args) {
        //并发: 多个线程操作同一个资源类, 把资源类丢入线程
        Ticket2 ticket = new Ticket2();

        new Thread(() -> {
            for (int i = 0; i < 5; i++)
                ticket.sale();
        }, "A").start();
        new Thread(() -> {
            for (int i = 0; i < 10; i++)
                ticket.sale();
        }, "B").start();
        new Thread(() -> {
            for (int i = 0; i < 40; i++)
                ticket.sale();
        }, "C").start();
    }
}


//资源类 OOP
//Lock三部曲
//1.new ReentrantLock();
//2.加锁
//3.释放锁
class Ticket2 {
    //属性, 方法
    private int number = 30;

    Lock lock = new ReentrantLock();

    //卖票
    public void sale() {
        lock.lock();
        lock.tryLock();
        try {
            //业务代码
            if (number > 0) {
                System.out.println(Thread.currentThread().getName() + "卖出了第" + (number--) + "张票, 剩余:" + number);
            }
        } finally {
            lock.unlock();
        }
    }
}
