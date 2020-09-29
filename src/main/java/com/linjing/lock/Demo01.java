package com.linjing.lock;

//可重入锁, 递归锁
//synchronized
public class Demo01 {
    public static void main(String[] args) {
        Phone phone = new Phone();
        new Thread(() -> {
            phone.sendMsg();
        }, "A").start();
        new Thread(() -> {
            phone.sendMsg();
        }, "B").start();
    }
}


class Phone {
    //外面一把锁
    public synchronized void sendMsg() {
        System.out.println(Thread.currentThread().getName() + ": send message.");
        call(); //里面一把锁
        //synchronized: call()里面的锁和外面的锁是同一把锁.
    }

    public synchronized void call() {
        System.out.println(Thread.currentThread().getName() + ": call.");
    }
}
