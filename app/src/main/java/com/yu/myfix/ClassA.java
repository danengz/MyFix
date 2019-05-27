package com.yu.myfix;

public class ClassA {

    public String methorA() {
        throw new RuntimeException("------异常------");
    }

    public String methorB() {
        return "我是methorB返回值";
    }
}
