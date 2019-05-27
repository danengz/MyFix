package com.yu.myfix.fixed;

import com.yu.myfix.Replace;

public class ClassA {

    @Replace(clazz = "com.yu.myfix.ClassA", method = "methorA")
    public String methorA() {

        return "我是已修复后的methorA返回值";

    }

    public String methorB() {

        return "我是已修复后的methorB返回值";

    }

}
