package com.stargazer.demos.java.string;

public class StrObjTest {
    public static void main(String[] args) {
        String s1="ab";
        String s2="a"+"b";
        String s3="a";
        String s4="b";
        String s5=s3+s4;
        System.out.println("s1==s2 : " + (s1 == s2));
        System.out.println("s1==s5 : " + (s1 == s5));
        System.out.println("s2==s5 : " + (s2 == s5));

        String s6=s1.intern();
        System.out.println("s1==s6 : " + (s1 == s6));//返回false
    }
}
