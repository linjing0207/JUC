package com.linjing.pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

//Executors 工具类, 三大方法
public class ExecutorsDemo01 {
    public static void main(String[] args) {
        //        ExecutorService threadPool = Executors.newSingleThreadExecutor();//单个线程
        //        ExecutorService threadPool = Executors.newFixedThreadPool(5);  //固定的线程池大小, 最多允许5个线程同时执行
        ExecutorService threadPool = Executors.newCachedThreadPool();  //可伸缩的, 遇强则强, 遇弱则弱的线程池

        //线程池使用完, 程序结束, 关闭线程池
        try {
            for (int i = 1; i <= 50; i++) {
                //使用了线程池之后, 要使用线程池来创建线程
                //不用原来的new Thread(()->{})来创建了
                threadPool.execute(() -> {
                    try {
                        TimeUnit.MILLISECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName());
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            threadPool.shutdown();
        }
    }
}
