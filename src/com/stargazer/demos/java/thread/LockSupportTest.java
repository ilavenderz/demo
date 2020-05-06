package com.stargazer.demos.java.thread;

import java.util.concurrent.locks.LockSupport;

public class LockSupportTest {
    static Thread t1 = null, t2 = null;

    public static void main(String[] args) {
        char[] aI = "123456789".toCharArray();
        char[] aC = "ABCDEFGHI".toCharArray();
        t1 = new Thread(() ->{
            for(char c : aI) {
                System.out.println(c);
                LockSupport.unpark(t2);
                LockSupport.park();
            }
            LockSupport.unpark(t2);
        },"t1");
        t2 = new Thread(() ->{
            for(char c : aC) {
                System.out.println(c);
                LockSupport.unpark(t1);
                LockSupport.park();
            }
            LockSupport.unpark(t1);
        },"t2");

        t1.start();
        t2.start();
    }
}
