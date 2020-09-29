package com.linjing.callback;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * 异步调用: Ajax
 * 异步执行
 * 成功回调
 * 失败回调
 */
public class Demo01 {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        //异步回调的任务
        //没有返回值的 runAsync 异步回调: 没有结果
        CompletableFuture<Void> completableFuture = CompletableFuture.runAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + ": runAsync=>Void");
        });
        //先执行
        System.out.println("1111");
        //异步回调的结果
        completableFuture.get(); //获取阻塞执行结果
    }
}
