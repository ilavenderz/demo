package com.stargazer.demos.java.cache;

public class CachePadding {
    private static class ShareValue {
        public volatile long x = 0L;
        public long p1,p2,p3,p4,p5,p6,p7;
    }

    public static ShareValue[] arr = new ShareValue[2];

    static {
        arr[0] = new ShareValue();
        arr[1] = new ShareValue();
    }

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            for(long i = 0; i < 10000000L; i++) {
                arr[0].x = i;
            }
        });
        Thread t2 = new Thread(() -> {
            for(long i = 0; i < 10000000L; i++) {
                arr[1].x = i;
            }
        });
        final long start = System.nanoTime();
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        final long end = System.nanoTime();
        System.out.println(end - start);
    }
}
