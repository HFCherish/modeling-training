package com.thoughtworks.mobileCharge.infrastructure.mongo;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.thoughtworks.mobileCharge.domain.test.MyTest;
import com.thoughtworks.mobileCharge.infrastructure.mappers.MyTestMapper;
import org.bson.Document;
import org.bson.types.ObjectId;

import javax.inject.Inject;
import java.util.Optional;

/**
 * Created by pzzheng on 12/7/16.
 */
public class MyTestDB implements MyTestMapper {

    private final MongoCollection<Document> testCollection;

    @Inject
    public MyTestDB(MongoDatabase db) {
        this.testCollection = db.getCollection("tests");
    }

    @Override
    public Optional<MyTest> findById(String id) {
        return Optional.of(buildMyTest(testCollection.find(Filters.eq("_id", id)).first()));
    }

    @Override
    public MyTest save(MyTest myTestObj) {
        testCollection.insertOne(new Document("_id", myTestObj.getId()).append("name", myTestObj.getName()));
        return buildMyTest(testCollection.find().first());
    }

    private MyTest buildMyTest(Document document) {
        if(document == null)    return null;
        return new MyTest(document.get("_id").toString(),
                document.get("name").toString());
    }
}
