package com.thoughtworks.mobileCharge.infrastructure.mongo;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.thoughtworks.mobileCharge.domain.user.MessageRecord;
import com.thoughtworks.mobileCharge.infrastructure.mappers.MessageRecordMapper;
import org.bson.Document;

import javax.inject.Inject;

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
    public MessageRecord saveMessage(MessageRecord newMessage) {
        messageRecords.insertOne(MessageRecord.toDocument(newMessage));
        return MessageRecord.buildFromDocument(messageRecords.find().first());
    }
}
