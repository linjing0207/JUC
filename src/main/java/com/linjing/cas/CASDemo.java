package com.linjing.cas;

import java.util.concurrent.atomic.AtomicInteger;

//CAS: compare and swap, 比较并交换
public class CASDemo {

    public static void main(String[] args) {
        AtomicInteger atomicInteger = new AtomicInteger(2020);

        //atomicInteger.compareAndSet(int expect, int update) 期望 更新
        //如果我期望的值达到了，就更新，否则就不更新。
        //CAS: 是CPU的并发原语, 是CPU指令.
        System.out.println(atomicInteger.compareAndSet(2020, 2021));
        System.out.println(atomicInteger.get()); //2021

        System.out.println(atomicInteger.compareAndSet(2020, 2021)); //false
        System.out.println(atomicInteger.get());  //2021

        atomicInteger.getAndIncrement(); //+1
        //java无法调用内存，java可以调用c++, c++可以操作内存。
        //java可以通过unsafe类操作内存
        //unsafe类里都是native方法
    }
}
