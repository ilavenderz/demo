package com.stargazer.demos.java.polym;

public class Test {

    public static void main(String[] args) {
        show(new Cat());
        show(new Dog());
        Animal a = new Cat();
        a.eat();
        Cat c = (Cat) a;
        c.work();
    }

    public static void show(Animal a){
        a.eat();
        if(a instanceof Cat){
            Cat c = (Cat) a;
            c.work();
        }else if(a instanceof Dog){
            Dog d = (Dog) a;
            d.work();
        }
    }
}

abstract class Animal{
    abstract void eat();
}

class Cat extends Animal{
    @Override
    public void eat() {
        System.out.println("吃鱼");
    }
    public void work(){
        System.out.println("抓老鼠");
    }
}

class Dog extends Animal{
    @Override
    void eat() {
        System.out.println("吃骨头");
    }
    public void work(){
        System.out.println("看家");
    }
}