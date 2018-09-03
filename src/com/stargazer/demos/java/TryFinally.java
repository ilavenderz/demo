package com.stargazer.demos.java;

public class TryFinally {
    public static void main(String[] args) {
        System.out.println(m_1());
        System.out.println(m_2());
        System.out.println(m_3());
        System.out.println(m_4());
        m_5();
        m_6();
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
                    Thread.sleep(100000);
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
                Thread.sleep(100);
                if(thread.isAlive())
                    thread.stop();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    private static void stop(Thread thread) {
        thread.stop();
    }
}
