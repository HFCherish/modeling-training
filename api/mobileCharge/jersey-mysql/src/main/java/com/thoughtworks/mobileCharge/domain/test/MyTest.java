package com.thoughtworks.mobileCharge.domain.test;

/**
 * Created by pzzheng on 12/7/16.
 */
public class MyTest {
    String id;

    String name;

    private MyTest() {
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public MyTest(String id, String name) {
        this.id = id;
        this.name = name;
    }
}
