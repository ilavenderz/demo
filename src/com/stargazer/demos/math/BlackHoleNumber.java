package com.stargazer.demos.math;

import com.istargazer.algorithm.loop.BlackHole;

import java.util.*;

public class BlackHoleNumber {

    public static void main(String[] args) {
        Map<Long,String> result = new HashMap<>();

        for (int i = 10000; i < 99999; i++) {
            long temp = BlackHole.calcBlackHoleNumber(i);
            if(result.containsKey(temp)){
                result.put(temp,result.get(temp) + "," + String.valueOf(i));
            } else {
                result.put(temp,String.valueOf(i));
            }
        }
        Set<Map.Entry<Long,String>> sets = result.entrySet();
        for(Map.Entry<Long,String> entry : sets){
            System.out.println(entry.getKey() + ":" + entry.getValue());
        }
    }
}
