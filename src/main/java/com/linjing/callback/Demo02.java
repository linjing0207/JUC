package com.linjing.callback;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * 异步调用: Ajax
 * 异步执行
 * 成功回调
 * 失败回调
 */
public class Demo02 {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        //异步回调的任务
        //有返回值的 supplyAsync 异步回调 : 有结果
        //返回的是错误信息
        CompletableFuture<Integer> completableFuture = CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread().getName() + ": supplyAsync => Integer");
            int i = 10 / 0;
            //成功的回调
            return 1024;
        });

        //异步回调的结果
        System.out.println(completableFuture.whenComplete((t, u) -> {
            System.out.println("t: " + t); //正常的返回结果
            System.out.println(
                "u: " + u); //错误信息: java.util.concurrent.CompletionException: java.lang.ArithmeticException: / by zero
        }).exceptionally(e -> {
            e.getMessage(); //打印异常信息
            //失败的回调
            return 233; //返回错误的返回结果.
        }).get());
    }
}
