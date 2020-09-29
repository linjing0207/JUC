package com.linjing.singleton;

import java.lang.reflect.Constructor;

//enum 本身也是一个class类
public enum EnumSingleton {
    INSTANCE;

    public static EnumSingleton getInstance() {
        return INSTANCE;
    }
}


class Test1 {
    public static void main(String[] args) throws Exception {
        EnumSingleton s1 = EnumSingleton.INSTANCE;
        EnumSingleton s2 = EnumSingleton.INSTANCE;
        System.out.println(s1 == s2); //true
        Constructor<EnumSingleton> declaredConstructor =
            EnumSingleton.class.getDeclaredConstructor(String.class, int.class);
        declaredConstructor.setAccessible(true);
        EnumSingleton s3 = declaredConstructor.newInstance();
        //java.lang.IllegalArgumentException: Cannot reflectively create enum objects
    }
}
