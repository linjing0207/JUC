package com.linjing.pool;

import java.util.concurrent.*;

public class ThreadPoolExecutorDemo {

    public static void main(String[] args) {
        //自定义线程池! 工作中都是ThreadPoolExecutor
        ExecutorService threadPool = new ThreadPoolExecutor(2, 5, 3, TimeUnit.SECONDS, new LinkedBlockingDeque<>(3),
            Executors.defaultThreadFactory(), new ThreadPoolExecutor.AbortPolicy());
        //这里的拒绝策略:
        //AbortPolicy: 银行的候客区都满了, 还有人进来, 不处理这个人的业务, 抛出异常
        //CallerRunsPolicy: 队列满了, 打发了, 哪儿来的回哪儿去.
        //DiscardPolicy: 队列满了, 丢掉任务, 不会抛出异常
        //DiscardOldestPolicy: 队列满了, 尝试和最早的竞争. 竞争成功, 则执行; 竞争失败, 则丢掉任务. 也不会抛出异常!


        try {
            //最大承受: queue + max
            //队列满了, 抛出异常 RejectedExecutionException
            for (int i = 1; i <= 10; i++) {
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
            //线程池使用完, 程序结束, 关闭线程池
            threadPool.shutdown();
        }
    }
}
