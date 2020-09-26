package com.linjing.unsafe;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

//java.util.ConcurrentModificationException
public class MapTest {
    public static void main(String[] args) {
        // Map<String, String> map = new HashMap<>();
        // map是这样用的吗? 工作中不这么用
        // 默认等价于什么? Map<String, String> map = new HashMap<>(16, 0.75);
        // 如果选择是无参的构造器的话，这里在new Node数组的时候会使用默认大小为DEFAULT_CAPACITY（16），然后乘以加载因子0.75为12，也就是说数组的可用大小为12。
        // 加载因子0.75 初始化容量: 16

        /**
         * 解决方案:
         * 1. Map<String, String> map = Collections.synchronizedMap(new HashMap<>());
         * 2. Map<String, String> map = new ConcurrentHashMap<>();
         */

        Map<String, String> map = new ConcurrentHashMap<>();

        for (int i = 1; i <= 10; i++) {
            new Thread(() -> {
                map.put(Thread.currentThread().getName(), UUID.randomUUID().toString().substring(0, 5));
                System.out.println(map);
            }, String.valueOf(i)).start();
        }
    }
}
