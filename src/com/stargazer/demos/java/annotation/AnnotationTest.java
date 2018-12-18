package com.stargazer.demos.java.annotation;

@Counter(count = 1)
public class AnnotationTest {

    public static void main(String[] args) {
        Counter counter = AnnotationTest.class.getAnnotation(Counter.class);
        System.out.println(counter.count());
    }
}
