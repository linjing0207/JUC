package com.linjing.pc;

/*
 * 线程之间的通信问题, 生产者和消费者问题!
 * 线程交替执行 A B 操作同一个变量
 * A num+1
 * B num-1
 */
public class Syn {
    public static void main(String[] args) {
        Data data = new Data();
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
class Data {
    private int number = 0;

    public synchronized void increase() {
        while (number != 0) { //如果是if, 会产生虚假唤醒问题.
            //等待
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        number++;
        System.out.println(Thread.currentThread().getName() + "=>" + number);
        //通知其他线程, 我+1完毕了
        this.notify();
    }

    public synchronized void decrease() {
        while (number == 0) {
            //等待
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        number--;
        //通知其他线程, 我-1完毕了
        System.out.println(Thread.currentThread().getName() + "=>" + number);
        this.notify();
    }
}
