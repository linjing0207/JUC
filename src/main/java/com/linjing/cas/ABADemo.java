package com.linjing.cas;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicStampedReference;

//ABA问题: 用原子引用来解决
public class ABADemo {

    public static void main(String[] args) {
        //Integer(-128, 127) 有缓存, 会复用已有对象, 所以不能用2020
        //AtomicStampedReference 如果泛型是包装类, 注意对象的引用问题
        //正常的业务操作, 一般会引用一个对象
        AtomicStampedReference<Integer> atomicStampedReference = new AtomicStampedReference<>(1, 1);
        //线程a
        new Thread(() -> {
            System.out.println("a1: " + atomicStampedReference.getStamp()); //获得版本号
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //A->B
            System.out.println("a2: " + atomicStampedReference
                .compareAndSet(1, 2, atomicStampedReference.getStamp(), atomicStampedReference.getStamp() + 1) + ", "
                + atomicStampedReference.getStamp());
            //B->A
            System.out.println("a3: " + atomicStampedReference
                .compareAndSet(2, 1, atomicStampedReference.getStamp(), atomicStampedReference.getStamp() + 1) + ", "
                + atomicStampedReference.getStamp());
        }, "a").start();

        //线程b
        new Thread(() -> {
            int stamp = atomicStampedReference.getStamp(); //获得版本号
            System.out.println("b1: " + stamp);
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //乐观锁相同原理
            //A->C, 由于版本号修改了, 所以修改失败
            System.out.println("b2: " + atomicStampedReference.compareAndSet(1, 6, stamp, stamp + 1) + ", " + stamp);
        }, "b").start();
    }
}
