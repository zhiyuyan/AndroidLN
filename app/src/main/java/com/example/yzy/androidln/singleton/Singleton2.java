package com.example.yzy.androidln.singleton;

// 懒汉式，线程安全
public class Singleton2 {

    private static Singleton2 instance;

    private Singleton2() {

    }

    public synchronized Singleton2 getInstance() {
        if (instance == null) {
            instance = new Singleton2();
        }
        return instance;
    }

}
