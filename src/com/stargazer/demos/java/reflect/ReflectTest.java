package com.stargazer.demos.java.reflect;

import com.stargazer.demos.java.TryFinally;

public class ReflectTest {

    public static void main(String[] args) throws ClassNotFoundException {
        Class<?> class1 = Class.forName("com.stargazer.demos.java.TryFinally");
        TryFinally tf = new TryFinally();
        Class<?> class2 = tf.getClass();
        Class<?> class3 = TryFinally.class;
        System.out.println("class1 <-> class2 : " + (class1 == class2));
        System.out.println("class1 <-> class3 : " + (class1 == class3));
        System.out.println("class2 <-> class3 : " + (class2 == class3));
    }
}
