package com.thoughtworks.mobileCharge.infrastructure.mongo;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.thoughtworks.mobileCharge.domain.user.CallRecord;
import com.thoughtworks.mobileCharge.domain.user.User;
import com.thoughtworks.mobileCharge.infrastructure.mappers.CallRecordMapper;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.joda.time.DateTime;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.mongodb.client.model.Filters.and;
import static com.thoughtworks.mobileCharge.infrastructure.util.FilterHelpers.getMonthFilter;

/**
 * Created by pzzheng on 12/9/16.
 */
public class CallRecordDB implements CallRecordMapper {

    private MongoCollection<Document> callRecords;

    @Inject
    public CallRecordDB(MongoDatabase db) {
        callRecords = db.getCollection("call_records");
    }

    @Override
    public long countOf(User user, int month) {
        Bson filter = Filters.eq("owner", user.getId().id());
        if( month>=1 && month<=12) {
            filter = and(filter, getMonthFilter(month));
        }
        return callRecords.count(filter);
    }

    @Override
    public List<CallRecord> findAllOf(User user, int month, int page, int perPage) {
        Bson filter = Filters.eq("owner", user.getId().id());
        if( month>=1 && month<=12) {
            filter = and(filter, getMonthFilter(month));
        }
        ArrayList<Document> documents = callRecords.find(filter).skip(page).limit(perPage).into(new ArrayList<>());
        return documents.stream().map(document -> CallRecord.buildFromDocument(document)).filter(callRecord -> callRecord != null).collect(Collectors.toList());
    }

    @Override
    public CallRecord saveCallRecord(CallRecord callRecord) {
        callRecords.insertOne(CallRecord.toDocument(callRecord));
        return CallRecord.buildFromDocument(callRecords.find().first());
    }
}
