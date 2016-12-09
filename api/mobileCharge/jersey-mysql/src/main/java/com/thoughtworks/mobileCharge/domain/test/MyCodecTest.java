package com.thoughtworks.mobileCharge.domain.test;

import org.bson.types.ObjectId;

/**
 * Created by pzzheng on 12/7/16.
 */
public class MyCodecTest {
    String id;

    String name;

    private MyCodecTest() {
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public MyCodecTest(String name) {
        this.name = name;
    }

    public MyCodecTest withNewId() {
        this.id = new ObjectId().toHexString();
        return this;
    }
}
