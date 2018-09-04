package com.stargazer.demos.java;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class TryFinally {
    private static final BlockingQueue<Integer> q1 = new LinkedBlockingQueue<>();
    private static final BlockingQueue<Integer> q2 = new LinkedBlockingQueue<>();
    public static void main(String[] args) throws InterruptedException {
        System.out.println(m_1());
        System.out.println(m_2());
        System.out.println(m_3());
        System.out.println(m_4());
        m_6();
        m_7();
        m_5();
        Thread.sleep(1000000);
    }

    public static int m_1(){
        System.out.println("m1-------------------------------");
        int i = 10;
        try {
            System.out.println("start");
            return i+=10;
        } catch (Exception e) {
            System.out.println("Error : " + e);
        } finally {
            System.out.println(i);
            System.out.println("end");
        }
        System.out.println("m1--------------------------------");
        return 0;
    }
    public static int m_2() {
        System.out.println("m2----------------------------------");
        int i = 10;
        try {
            System.out.println("start");
            return i += 10;
        } catch (Exception e) {
            System.out.println("Error : " + e);
        } finally {
            System.out.println(i);
            System.out.println("end");
            System.out.println("m2----------------------------------------");
            return 50;
        }
    }
    public static int m_3() {
        System.out.println("m3--------------------------------");
        int i = 10;
        try {
            System.out.println("start");
            return i += 10;
        } catch (Exception e) {
            System.out.println("Error : " + e);
        } finally {
            System.out.println(i);
            System.out.println("end");
            i = 50;
        }
        System.out.println("m3------------------------------------");
        return i;
    }
    public static int m_4() {
        System.out.println("m4---------------------------------");
        int i = 10;
        try {
            i = 10 / 0;
            try {
                System.out.println("try block");
            } catch (Exception e) {
                System.out.println("inner exception block");
            } finally {
                System.out.println("finally block");
            }
        } catch (Exception e) {
            System.out.println("outer exception block");
        }
        System.out.println("m4------------------------------------");
        return i;
    }
    public static void m_5() {

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("m5--------------------------------------");
                try {
                    System.out.println("try block");
                    System.exit(0);
                } catch (Exception e) {
                    System.out.println("exception block");
                } finally {
                    System.out.println("finally block");
                }
                System.out.println("m5---------------------------------------");
            }
        });
        thread.start();
    }
    public static void m_6() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("m6-------------------------------");
                try {
                    System.out.println("thread try block");
                    q1.put(1);
                    while(true) {
                        Integer i = q1.peek();
                        if(null != i && 2 == i)
                            break;
                    }
                } catch (Exception e) {
                    System.out.println("exception block");
                } finally {
                    System.out.println("finally block");
                }
                System.out.println("m6-------------------------------");
            }
        });
        thread.start();
        while(true){
            try {
                Thread.sleep(10);
                if(1 == q1.take()){
                    thread.stop();
                    q1.put(2);
                    break;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void m_7() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("m7-------------------------------");
                try {
                    System.out.println("interrupt thread try block");
                    q2.put(1);
                    while(true) {
                        Integer i = q2.peek();
                        if(null != i && 2 == i)
                            break;
                    }
                } catch (Exception e) {
                    System.out.println("interrupt exception block");
                } finally {
                    System.out.println("finally block");
                }
                System.out.println("m7-------------------------------");
            }
        });
        thread.start();
        while(true){
            try {
                Thread.sleep(10);
                if(1 == q2.take()) {
                    thread.interrupt();
                    q2.put(2);
                    break;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
