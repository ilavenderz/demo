package com.stargazer.demos.str;

public class StringTest {
    public static void main(String[] args) {
        String str1 = "通话";
        String str2 = "重地";
        System.out.printf("str1:%d,str2:%d%n", str1.hashCode(), str2.hashCode());
        System.out.println(str1.equals(str2));
        System.out.println(Math.round(-1.5));
        System.out.println((new StringBuilder(str1)).reverse());
        System.out.println((new StringBuilder(str2)).reverse());
    }
}
