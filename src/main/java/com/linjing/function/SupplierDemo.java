package com.linjing.function;

import java.util.function.Supplier;

//Supplier 供给型接口 没有参数, 只有返回值
public class SupplierDemo {
    public static void main(String[] args) {
        //Supplier<String> supplier = new Supplier<String>() {
        //    @Override
        //    public String get() {
        //        System.out.println("get()");
        //        return "1234";
        //    }
        //};

        Supplier<String> supplier = () -> {
            System.out.println("get()");
            return "1234";
        };
        System.out.println(supplier.get());
    }
}
