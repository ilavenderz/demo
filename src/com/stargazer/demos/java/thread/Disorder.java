package com.stargazer.demos.java.thread;

public class Disorder {

    private static int a = 0, b = 0, x = 0, y = 0;

    public static void main(String[] args) throws InterruptedException {
        boolean flag = true;
        while(flag) {
            a = 0;
            b = 0;
            x = 0;
            y = 0;
            Thread one = new Thread(() -> {
               a = 1;
               x = b;
            });
            Thread two = new Thread(() -> {
                b = 1;
                y = a;
            });
            one.start();
            two.start();
            one.join();
            two.join();
            if(x == 0 && y == 0) {
                flag = false;
                System.out.println("x=0,y=0");
            }
        }
    }
}
