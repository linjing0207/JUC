package com.linjing.bq;


import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

public class BlockingQueueDemo {
    public static void main(String[] args) throws InterruptedException {
        //        test1();
        //        test2();
        //        test3();
        test4();
    }

    //抛出异常
    public static void test1() {
        //队列大小
        ArrayBlockingQueue blockingQueue = new ArrayBlockingQueue<>(3);

        //Exception in thread "main" java.util.NoSuchElementException
        //        System.out.println(blockingQueue.element());

        System.out.println(blockingQueue.add("a"));
        System.out.println(blockingQueue.add("b"));
        System.out.println(blockingQueue.add("c"));
        //抛出异常
        //Exception in thread "main" java.lang.IllegalStateException: Queue full
        //System.out.println(blockingQueue.add("d"));

        System.out.println("===============");

        System.out.println(blockingQueue.remove());
        System.out.println(blockingQueue.remove());
        System.out.println(blockingQueue.remove());
        //抛出异常
        //Exception in thread "main" java.util.NoSuchElementException
        //System.out.println(blockingQueue.remove());
    }

    //有返回值, 没有异常
    public static void test2() {
        ArrayBlockingQueue blockingQueue = new ArrayBlockingQueue<>(3);

        System.out.println(blockingQueue.peek());
        System.out.println(blockingQueue.offer("a"));
        System.out.println(blockingQueue.offer("b"));
        System.out.println(blockingQueue.offer("c"));
        //false, 不抛出异常
        System.out.println(blockingQueue.offer("d"));

        System.out.println("===============");

        System.out.println(blockingQueue.poll());
        System.out.println(blockingQueue.poll());
        System.out.println(blockingQueue.poll());
        //null 不抛出异常
        System.out.println(blockingQueue.poll());
    }

    //等待, 一直阻塞
    public static void test3() throws InterruptedException {
        ArrayBlockingQueue blockingQueue = new ArrayBlockingQueue<>(3);

        blockingQueue.put("a");
        blockingQueue.put("b");
        blockingQueue.put("c");
        //        blockingQueue.put("d"); //队列没有位置了, 一直阻塞

        System.out.println(blockingQueue.take());
        System.out.println(blockingQueue.take());
        System.out.println(blockingQueue.take());
        //        System.out.println(blockingQueue.take());  //队列没有元素了, 一直阻塞

    }

    //等待, 等待超时
    public static void test4() throws InterruptedException {
        ArrayBlockingQueue blockingQueue = new ArrayBlockingQueue<>(3);

        blockingQueue.offer("a");
        blockingQueue.offer("b");
        blockingQueue.offer("c");
        //offer()的重载方法
        //等待超过2s, 就结束了
        blockingQueue.offer("d", 2, TimeUnit.SECONDS);

        System.out.println("===============");

        System.out.println(blockingQueue.poll());
        System.out.println(blockingQueue.poll());
        System.out.println(blockingQueue.poll());
        //poll()的重载方法
        //等待超过2s, 就退出
        System.out.println(blockingQueue.poll(2, TimeUnit.SECONDS));

    }
}
