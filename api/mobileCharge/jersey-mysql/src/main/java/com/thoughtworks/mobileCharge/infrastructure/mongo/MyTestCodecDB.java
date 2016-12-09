package com.thoughtworks.mobileCharge.infrastructure.mongo;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.thoughtworks.mobileCharge.domain.test.MyCodecTest;
import com.thoughtworks.mobileCharge.infrastructure.mappers.MyTestCodecMapper;

import javax.inject.Inject;
import java.util.Optional;

/**
 * Created by pzzheng on 12/7/16.
 */
public class MyTestCodecDB implements MyTestCodecMapper {

    private final MongoCollection<MyCodecTest> testCollection;

    @Inject
    public MyTestCodecDB(MongoDatabase db) {
        this.testCollection = db.getCollection("tests", MyCodecTest.class);
    }

    @Override
    public Optional<MyCodecTest> findById(String id) {
        return Optional.ofNullable(testCollection.find(Filters.eq("_id", id)).first());
    }

    @Override
    public MyCodecTest save(MyCodecTest myTestObj) {
        testCollection.insertOne(myTestObj);
        return testCollection.find().first();
    }
}
