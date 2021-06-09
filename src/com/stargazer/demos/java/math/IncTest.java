package com.stargazer.demos.java.math;

public class IncTest {
    volatile int index = 0;

    public void add() {
        index = index++;
        System.out.println(index);
    }
    public static void main(String[] args) {
        int j = 0;
        for(int i = 0; i < 10; i++) {
            j = (j++);
        }
        System.out.println(j);
        j = 0;
        for(int i = 0; i < 10; i++) {
            j = j++;
        }
        System.out.println(j);
        j = 0;
        for(int i = 0; i < 10; i++) {
            j = (++j);
        }
        System.out.println(j);
        j = 0;
        for(int i = 0; i < 10; i++) {
            j = ++j;
        }
        System.out.println(j);
        IncTest it = new IncTest();
        it.add();
    }
}
