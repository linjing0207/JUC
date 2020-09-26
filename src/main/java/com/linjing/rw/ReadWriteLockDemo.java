package com.linjing.rw;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 独占锁(写锁), 共享锁(读锁)
 * ReadWriteLock
 * 读-读 可以共存
 * 读-写 不能共存!
 * 写-写 不能共存!
 */
public class ReadWriteLockDemo {
    public static void main(String[] args) {
        MyCacheLock myCache = new MyCacheLock();
        //写入
        for (int i = 1; i <= 10; i++) {
            final int temp = i;
            new Thread(() -> {
                myCache.put(String.valueOf(temp), String.valueOf(temp));
            }, "写入线程" + i).start();
        }
        //读取
        for (int i = 1; i <= 10; i++) {
            final int temp = i;
            new Thread(() -> {
                myCache.get(String.valueOf(temp));
            }, "读取线程" + i).start();
        }
    }
}


/**
 * 自定义缓存
 */

class MyCache {
    private volatile Map<String, Object> map = new HashMap<>();

    //存, 写
    public void put(String key, Object value) {
        System.out.println(Thread.currentThread().getName() + "写入" + key);
        map.put(key, value);
        System.out.println(Thread.currentThread().getName() + "写入成功!");

    }

    //取, 读
    public void get(String key) {
        System.out.println(Thread.currentThread().getName() + "读取" + key);
        Object o = map.get(key);
        System.out.println(Thread.currentThread().getName() + "读取成功!");
    }
}


class MyCacheLock {
    private volatile Map<String, Object> map = new HashMap<>();
    //ReadWriteLock是接口, ReentrantReadWriteLock是实现类
    //这是读写锁, 更加细粒度的控制
    private ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    //存, 写: 写入的时候, 只希望同时只有一个线程写
    public void put(String key, Object value) {
        readWriteLock.writeLock().lock();
        //用Lock，必须主动去释放锁，并且在发生异常时，不会自动释放锁。
        //因此一般来说，使用Lock必须在try{}catch{}块中进行，并且将释放锁的操作放在finally块中进行，以保证锁一定被被释放，防止死锁的发生。
        try {
            System.out.println(Thread.currentThread().getName() + "写入" + key);
            map.put(key, value);
            System.out.println(Thread.currentThread().getName() + "写入成功!");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            readWriteLock.writeLock().unlock();
        }
    }

    //取, 读: 所有线程都可以读
    public void get(String key) {
        readWriteLock.readLock().lock();
        try {
            System.out.println(Thread.currentThread().getName() + "读取" + key);
            map.get(key);
            System.out.println(Thread.currentThread().getName() + "读取成功!");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            readWriteLock.readLock().unlock();
        }
    }
}
