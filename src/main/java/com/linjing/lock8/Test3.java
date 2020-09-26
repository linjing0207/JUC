package com.linjing.lock8;

import java.util.concurrent.TimeUnit;

/**
 * 关于锁的八个问题
 * 5. 给两个同步方法加上静态static关键字, 只有一个对象,
 * 那么线程A: 静态同步方法sendMsg(延迟4s)和 1s后的线程B:静态同步方法call()两个线程谁先执行?
 * --> sendMsg()先打印. 因为两个同步方法都是静态方法, 锁的是Class模板, 全局唯一. 就看谁先获得锁了.
 * 然而hello()是普通方法, 不受锁的影响; 即使有static, 也不受锁的影响.
 * 6. 创建了2个对象, 给两个同步方法加上静态static关键字,
 * 那么线程A:phone1的静态同步方法sendMsg(延迟4s)和 1s后的线程B: phone2的静态同步方法call()两个线程谁先执行?
 * --> sendMsg()先打印. 即使是两个对象, 但两个对象的类模板只有一个. 这里锁的是class.
 * 因为两个同步方法都是静态方法, 锁的是Class模板, 全局唯一. 就看谁先获得锁了.
 */
public class Test3 {
    public static void main(String[] args) {
        //两个对象, 这两个对象的class模板只有一个, static, 锁的是class
        Phone3 phone1 = new Phone3();
        Phone3 phone2 = new Phone3();

        //锁的存在
        new Thread(() -> {
            phone1.sendMsg();
        }, "A").start();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //问题5
        //        new Thread(() -> {
        //            phone1.call();
        //        }, "B").start();

        //问题6
        new Thread(() -> {
            phone2.call();
        }, "B").start();
    }
}


//Phone3是唯一的一个class对象
//Class<Phone3> phone3Class = Phone3.class;
class Phone3 {

    //synchronized 锁的对象是方法的调用者!
    //两个方法用的同一个锁, 谁先拿到, 谁执行!

    //static静态方法
    //类一加载就有了! 锁的是Class模板, 全局唯一.
    public static synchronized void sendMsg() {

        try {
            TimeUnit.SECONDS.sleep(4);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("发短信");
    }

    //打电话
    public static synchronized void call() {
        System.out.println("打电话");
    }

}
