package com.linjing.lock8;

import java.util.concurrent.TimeUnit;

/**
 * 关于锁的八个问题
 * 7. 同一个对象的, 那么线程A: 静态同步方法sendMsg(延迟4s)和 1s后的线程B: 普通同步方法call()两个线程谁先执行?
 * --> call()先打印. 因为静态同步方法, 锁class模板; 普通同步方法, 锁调用者(也就是对象).
 * 两把锁, 互不影响. 一起执行, 看谁先执行好了.
 * 8. 创建了2个对象, 那么线程A: phone1的同步方法sendMsg(延迟4s)和 1s后的线程B: phone2的同步方法call()两个线程谁先执行?
 * --> call()先打印. 虽然是两个对象, phone1是锁class, phone2是phone2对象锁. 有两把锁, 互不影响.
 */
public class Test4 {
    public static void main(String[] args) {
        //两个对象, 这两个对象的class模板只有一个, static, 锁的是class
        Phone4 phone1 = new Phone4();
        Phone4 phone2 = new Phone4();

        //锁的存在
        new Thread(() -> {
            phone1.sendMsg();
        }, "A").start();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //问题7
        //        new Thread(() -> {
        //            phone1.call();
        //        }, "B").start();

        //问题8
        new Thread(() -> {
            phone2.call();
        }, "B").start();
    }
}


//Phone3是唯一的一个class对象
//Class<Phone3> phone3Class = Phone3.class;
class Phone4 {

    //synchronized 锁的对象是方法的调用者!
    //两个方法用的同一个锁, 谁先拿到, 谁执行!

    //static静态同步方法
    //类一加载就有了!
    //锁的是Class模板, 全局唯一.
    public static synchronized void sendMsg() {

        try {
            TimeUnit.SECONDS.sleep(4);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("发短信");
    }

    //打电话:
    //普通同步方法
    //锁的调用者, 也就是new出来的对象. 不需要去等待class那个锁.
    public synchronized void call() {
        System.out.println("打电话");
    }

}
