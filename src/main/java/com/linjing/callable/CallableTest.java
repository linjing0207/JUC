package com.linjing.callable;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class CallableTest {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        //Thread构造参数只能是Runnable接口
        //        new Thread(new Runnable()).start();
        //        new Thread(new FutureTask<V>()).start();
        //        new Thread(new FutureTask<V>(Callable)).start();

        MyThread t = new MyThread();
        FutureTask<Integer> futureTask = new FutureTask<>(t);
        //适配类
        new Thread(futureTask, "A").start();
        new Thread(futureTask, "B").start();  //坑2: 结果会被缓存
        //        new Thread(new FutureTask<>(new MyThread()), "A").start();
        //获取Callable的返回结果
        Integer i = futureTask.get(); //坑1: 这个get方法可能会产生呢阻塞, 把它放到最后,或通过异步通信来处理
        System.out.println(i);
    }
}


class MyThread implements Callable<Integer> {

    @Override
    public Integer call() throws Exception {
        System.out.println("call()");
        return 1024;
    }
}
