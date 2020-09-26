package com.linjing.lock8;

import java.util.concurrent.TimeUnit;

/**
 * 关于锁的八个问题
 * 3. 增加了一个普通方法hello, 那么线程A:同步方法sendMsg(延迟4s)和 1s后的线程B: hello两个线程谁先执行?
 * --> hello()先打印. 因为hello是普通方法, 没有锁, 不受锁的影响.
 * 4.创建了2个对象, 那么线程A:phone1的同步方法sendMsg(延迟4s)和 1s后的线程B: phone2的同步方法call()两个线程谁先执行?
 * --> call()先打印. 因为synchronized 锁的对象是方法的调用者, 这里是两个不同的对象(调用者), 即2把不同的锁. 所以按照时间来的.
 */
public class Test2 {
    public static void main(String[] args) {
        //两个对象
        Phone2 phone1 = new Phone2();
        Phone2 phone2 = new Phone2();

        //锁的存在
        new Thread(() -> {
            phone1.sendMsg();
        }, "A").start();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //问题3
        //        new Thread(() -> {
        //            phone1.hello();
        //        }, "B").start();

        //问题4
        new Thread(() -> {
            phone2.call();
        }, "B").start();
    }
}


class Phone2 {

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

    //打招呼
    //这里没有锁: 不是同步方法, 不受锁的影响.
    public void hello() {
        System.out.println("hello");
    }
}
