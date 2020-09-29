package com.linjing.forkjoin;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.stream.LongStream;

public class Test {
    final static Long v1 = 0L;
    final static Long v2 = 30_0000_0000L;

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        //testSum();  //2638
        //testForkJoin();  //2628
        testStream();  //1211
    }

    //普通: for循环
    public static void testSum() {
        long sum = 0L;
        long start = System.currentTimeMillis();
        for (long i = v1; i <= v2; i++) {
            sum += i;
        }
        long end = System.currentTimeMillis();
        System.out.println("sum = " + sum + ", 时间:" + (end - start));
    }

    //forkJoin
    public static void testForkJoin() throws ExecutionException, InterruptedException {
        long start = System.currentTimeMillis();
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        ForkJoinTask<Long> submit = forkJoinPool.submit(new ForkJoinDemo(v1, v2)); //提交任务
        Long sum = submit.get();
        long end = System.currentTimeMillis();
        System.out.println("sum = " + sum + ", 时间:" + (end - start));
    }

    //stream并行流
    public static void testStream() {
        long start = System.currentTimeMillis();
        //stream并行流 (] 左开右闭
        long sum = LongStream.rangeClosed(v1, v2).parallel().reduce(0, Long::sum);
        long end = System.currentTimeMillis();
        System.out.println("sum = " + sum + ", 时间:" + (end - start));
    }
}
