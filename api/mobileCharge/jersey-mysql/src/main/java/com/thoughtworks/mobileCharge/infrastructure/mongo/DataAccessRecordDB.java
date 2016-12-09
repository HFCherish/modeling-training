package com.thoughtworks.mobileCharge.infrastructure.mongo;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.thoughtworks.mobileCharge.domain.user.DataAccessRecord;
import com.thoughtworks.mobileCharge.domain.user.MessageRecord;
import com.thoughtworks.mobileCharge.domain.user.User;
import com.thoughtworks.mobileCharge.infrastructure.mappers.DataAccessRecordMapper;
import org.bson.Document;
import org.bson.conversions.Bson;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.mongodb.client.model.Filters.and;
import static com.thoughtworks.mobileCharge.infrastructure.util.FilterHelpers.getMonthFilter;

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

    @Override
    public long countOf(User user, int month) {
        Bson filter = Filters.eq("owner", user.getId().id());
        if (month >= 1 && month <= 12) {
            filter = and(filter, getMonthFilter(month, "createdAt"));
        }
        return dataAccessRecords.count(filter);
    }

    @Override
    public List<DataAccessRecord> findAllOf(User user, int month, int page, int perPage) {
        Bson filter = Filters.eq("owner", user.getId().id());
        if (month >= 1 && month <= 12) {
            filter = and(filter, getMonthFilter(month, "createdAt"));
        }
        ArrayList<Document> documents = dataAccessRecords.find(filter).skip(page).limit(perPage).into(new ArrayList<>());
        return documents.stream().map(document -> DataAccessRecord.buildFromDocument(document)).filter(callRecord -> callRecord != null).collect(Collectors.toList());

    }
}
