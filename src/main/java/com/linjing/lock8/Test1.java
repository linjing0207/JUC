package com.linjing.lock8;

import java.util.concurrent.TimeUnit;

/**
 * 关于锁的八个问题
 * 1. 标准情况下, 一个对象, 线程A: 同步方法sentMsg(), 1s后的线程B: 同步方法call(), 这两个线程谁先执行?
 * --> sentMsg()先打印. 这两个方法用的同一把锁, 所以谁先拿到就谁先执行. sentMsg先拿到的.
 * 2. 线程A: 同步方法sentMsg(延迟4s), 1s后线程B: 同步方法call(), 这两个线程谁先执行?
 * --> sentMsg()先打印. synchronized 锁的对象是方法的调用者! sleep不释放锁.
 * 这两个方法用的同一把锁, 所以谁先拿到就谁先执行. sentMsg先拿到的.
 */
public class Test1 {
    public static void main(String[] args) {
        Phone phone = new Phone();

        //锁的存在
        new Thread(() -> {
            phone.sendMsg();
        }, "A").start();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Thread(() -> {
            phone.call();
        }, "B").start();
    }
}


class Phone {

    //synchronized 锁的对象是方法的调用者!
    //两个方法用的同一个锁, 谁先拿到, 谁执行!

    //发短信
    public synchronized void sendMsg() {
        try {
            TimeUnit.SECONDS.sleep(4);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("发短信");
    }

    //打电话
    public synchronized void call() {
        System.out.println("打电话");
    }
}
