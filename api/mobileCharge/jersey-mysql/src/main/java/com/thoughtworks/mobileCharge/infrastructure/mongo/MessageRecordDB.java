package com.thoughtworks.mobileCharge.infrastructure.mongo;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.thoughtworks.mobileCharge.domain.user.MessageRecord;
import com.thoughtworks.mobileCharge.domain.user.User;
import com.thoughtworks.mobileCharge.infrastructure.mappers.MessageRecordMapper;
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
public class MessageRecordDB implements MessageRecordMapper {

    private MongoCollection<Document> messageRecords;

    @Inject
    public MessageRecordDB(MongoDatabase db) {
        messageRecords = db.getCollection("call_records");
    }

    @Override
    public List<MessageRecord> findAllOf(User user, int month, int page, int perPage) {
        Bson filter = Filters.eq("owner", user.getId().id());
        if (month >= 1 && month <= 12) {
            filter = and(filter, getMonthFilter(month, "createdAt"));
        }
        ArrayList<Document> documents = messageRecords.find(filter).skip(page).limit(perPage).into(new ArrayList<>());
        return documents.stream().map(document -> MessageRecord.buildFromDocument(document)).filter(callRecord -> callRecord != null).collect(Collectors.toList());
    }

    @Override
    public long countOf(User user, int month) {
        Bson filter = Filters.eq("owner", user.getId().id());
        if (month >= 1 && month <= 12) {
            filter = and(filter, getMonthFilter(month, "createdAt"));
        }
        return messageRecords.count(filter);

    }

    @Override
    public MessageRecord saveMessage(MessageRecord newMessage) {
        messageRecords.insertOne(MessageRecord.toDocument(newMessage));
        return MessageRecord.buildFromDocument(messageRecords.find().first());
    }
}
