package com.linjing.demo01;

//买票例子


/**
 * 公司中的多线程开发, 降低耦合性
 * 线程就是一个单独的资源类, 没有任何附属的操作
 * 1. 属性, 方法
 */
public class SaleTicketDemo01 {
    public static void main(String[] args) {
        //并发: 多个线程操作同一个资源类, 把资源类丢入线程
        Ticket ticket = new Ticket();

        //@FunctionalInterface 函数式接口
        //lambda表达式: (()->{})
        //        new Thread(new Runnable() {
        //            @Override
        //            public void run() {
        //
        //            }
        //        }).start();
        new Thread(() -> {
            for (int i = 0; i < 40; i++) {
                ticket.sale();
            }
        }, "A").start();
        new Thread(() -> {
            for (int i = 0; i < 40; i++) {
                ticket.sale();
            }
        }, "B").start();
        new Thread(() -> {
            for (int i = 0; i < 40; i++) {
                ticket.sale();
            }
        }, "C").start();
    }
}


//资源类 OOP
class Ticket {
    //属性, 方法
    private int number = 50;

    //卖票
    //synchronized 本质: 队列, 锁
    public synchronized void sale() {
        if (number > 0) {
            System.out.println(Thread.currentThread().getName() + "卖出了第" + (number--) + "张票, 剩余:" + number);
        }
    }
}
