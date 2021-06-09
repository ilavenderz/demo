package com.stargazer.demos.java.classes;

public class Child extends Parent{
    public static String getClassesInfo(){
        return "child";
    }

    public static void main(String[] args) {
        System.out.println(Child.getClassesInfo());
    }
}
