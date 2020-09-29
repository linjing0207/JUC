package com.linjing.singleton;

import java.lang.reflect.Constructor;

public class Test {
    public static void main(String[] args) throws Exception {
        LazySingleton instance1 = LazySingleton.getInstance();
        Constructor<LazySingleton> declaredConstructor = LazySingleton.class.getDeclaredConstructor(null);
        declaredConstructor.setAccessible(true);
        LazySingleton instance2 = declaredConstructor.newInstance();
        LazySingleton instance3 = declaredConstructor.newInstance();
        System.out.println(instance1 == instance2); //false
        //反射破坏了单例模式的机制, 1和2不是同一个实例了.
        //若在LazySingleton的私有构造器中, 同步块判断singleton已经初始化则抛出异常, 可以让1==2
        //但是通过反射机制获得的两个实例依然不是同一个实例
        System.out.println(instance2 == instance3); //false
        //或者设置一个flag, 也可以限制反射. 但是一旦知道flag的成员名, 反射依然可以破坏单例模式.
        //==> 查看newInstance源码,
        //if ((clazz.getModifiers() & Modifier.ENUM) != 0)
        //   throw new IllegalArgumentException("Cannot reflectively create enum objects");
        //使用枚举类来解决问题
    }
}
