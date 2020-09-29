package com.linjing.forkjoin;

import java.util.concurrent.RecursiveTask;

/**
 * 求和计算的任务
 * 1. for循环
 * 2. forkJoin
 * 3. Stream
 */


/**
 * 如何使用forkJoin
 * 1. forkJoinPool 通过它来执行
 * 2. 计算任务forkJoinPool.execute(ForkJoinTask<?> task)
 * 3. 计算类要继承ForkJoinTask
 */
public class ForkJoinDemo extends RecursiveTask<Long> {
    private Long start;  //1
    private long end;    //10_0000_0000
    //临界值
    private Long temp = 10000L;

    public ForkJoinDemo(Long start, long end) {
        this.start = start;
        this.end = end;
    }

    //计算方法
    @Override
    protected Long compute() {
        if (end - start < temp) {
            long sum = 0L;
            for (long i = start; i <= end; i++) {
                sum += i;
            }
            return sum;
        } else {
            //forkJoin: 分支合并计算
            long mid = (start + end) / 2; //中间值
            ForkJoinDemo task1 = new ForkJoinDemo(start, mid);
            task1.fork(); //拆分任务, 把任务压入线程队列
            ForkJoinDemo task2 = new ForkJoinDemo(mid + 1, end);
            task2.fork(); //拆分任务, 把任务压入线程队列
            return task1.join() + task2.join();  //将结果合并
        }
    }
}
