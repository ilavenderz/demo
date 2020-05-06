package com.stargazer.demos.java.memory;

import com.stargazer.demos.common.entity.User;
import org.openjdk.jol.info.ClassLayout;

public class ObjectMemoryLayout {

    public static void main(String[] args) {
        User user = new User();
        String str = ClassLayout.parseInstance(user).toPrintable();
        System.out.println(str);
    }
}
