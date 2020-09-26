package com.linjing.function;

import java.util.function.Function;

/**
 * Function 函数式接口
 * 1. 有一个输入参数, 有一个输出
 * 2. 只要是函数式接口, 就可以用lambda表达式简化
 */
public class FunctionDemo {
    public static void main(String[] args) {
        //工具类:
        //Function<Integer, String> function = new Function<Integer, String>() {
        //    @Override
        //    public String apply(Integer i) {
        //        return String.valueOf(i);
        //    }
        //};

        //简化
        Function<Integer, String> function = (i) -> {
            return String.valueOf(i);
        };
        //Function<Integer, String> function = (i) -> String.valueOf(i);

        //Function<Integer, String> function = String::valueOf;
        System.out.println(function.apply(5));
    }
}
