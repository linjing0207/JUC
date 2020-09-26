package com.linjing.unsafe;

import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArraySet;

//java.util.ConcurrentModificationException 并发修改异常
public class SetTest {
    public static void main(String[] args) {
        // 并发下 HashSet 不安全
        //        Set<String> set = new HashSet<>();
        /**
         * 解决方案:
         * 1. Set<String> set = Collections.synchronizedSet(new HashSet<>());
         * 2. Set<String> set = new CopyOnWriteArraySet<>();
         **/
        Set<String> set = new CopyOnWriteArraySet<>();
        for (int i = 1; i <= 10; i++) {
            new Thread(() -> {
                set.add(UUID.randomUUID().toString().substring(0, 5));
                System.out.println(set);
            }, String.valueOf(i)).start();
        }
    }
}
