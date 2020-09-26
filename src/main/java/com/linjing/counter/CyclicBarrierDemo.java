package com.linjing.counter;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierDemo {
    public static void main(String[] args) {
        //集卡
        CyclicBarrier cyclicBarrier = new CyclicBarrier(7, () -> System.out.println("兑换奖品."));
        for (int i = 1; i <= 7; i++) {
            final int temp = i;
            //匿名内部方法能操作到i吗? -->不能吗
            //因为线程也是一个类，所以new Thread也相当于创建了一个内部类
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName() + ": 收集到了第" + temp + "张卡");
                try {
                    cyclicBarrier.await();
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }, String.valueOf(i)).start();
        }
    }
}
