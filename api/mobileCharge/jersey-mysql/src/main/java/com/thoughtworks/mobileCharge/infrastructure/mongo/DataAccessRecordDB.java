package com.thoughtworks.mobileCharge.infrastructure.mongo;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.thoughtworks.mobileCharge.domain.user.DataAccessRecord;
import com.thoughtworks.mobileCharge.infrastructure.mappers.DataAccessRecordMapper;
import org.bson.Document;

import javax.inject.Inject;

/**
 * Created by pzzheng on 12/9/16.
 */
public class DataAccessRecordDB implements DataAccessRecordMapper {

    private MongoCollection<Document> dataAccessRecords;

    @Inject
    public DataAccessRecordDB(MongoDatabase db) {
        dataAccessRecords = db.getCollection("data_access_records");
    }

    @Override
    public DataAccessRecord saveDataAccessRecord(DataAccessRecord newDataAccess) {
        dataAccessRecords.insertOne(DataAccessRecord.toDocument(newDataAccess));
        return DataAccessRecord.buildFromDocument(dataAccessRecords.find().first());

    }
}
