package com.thoughtworks.mobileCharge.domain.user;

import com.thoughtworks.mobileCharge.api.jersey.Routes;
import com.thoughtworks.mobileCharge.domain.*;
import com.thoughtworks.mobileCharge.infrastructure.records.Record;
import com.thoughtworks.mobileCharge.util.IdGenerator;
import com.thoughtworks.mobileCharge.util.LocaleFormatter;
import org.bson.Document;
import org.joda.time.DateTime;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static java.util.Arrays.asList;

/**
 * Created by pzzheng on 11/30/16.
 */
public class MessageRecord extends CommunicationRecord implements Record {
    protected Locale from_locale;
    protected PhoneCard targetCard;
    protected Type type;
    protected SendType sendType;
    protected Long createdAt;
    protected EntityId id;
    protected EntityId ownerId;

    private MessageRecord() {
    }

    public MessageRecord(User owner, Locale from_locale, PhoneCard targetCard, Type type, SendType sendType, Long createdAt) {
        this.id = new EntityId(IdGenerator.next());
        this.ownerId = owner.getId();
        this.from_locale = from_locale;
        this.targetCard = targetCard;
        this.type = type;
        this.sendType = sendType;
        this.createdAt = createdAt;

        this.communicationType = CommunicationType.typeOf(from_locale, targetCard.locale);
        this.fee = owner.getBalance().charge(this, Balance.ChargeStrategies.messageCharge());
    }

    public EntityId getId() {
        return id;
    }

    @Override
    public Map<String, Object> toRefJson(Routes routes) {
        return new HashMap() {{
            put("id", id.id());
            put("type", type.name());
            put("send_type", sendType.name());
            put("createdAt", new DateTime(createdAt));
            put("from_locale", LocaleFormatter.getCityAndCountry(from_locale));
            put("targetCard", targetCard.toRefJson(routes));
            put("communication_type", communicationType.name());
            put("fee", fee);
            put("links", asList(
                    routes.linkMap("self", routes.messageRecordUrl(ownerId.id(), id.id()).getPath())
            ));
        }};
    }

    @Override
    public Map<String, Object> toJson(Routes routes) {
        return toRefJson(routes);
    }

    public static Document toDocument(MessageRecord newMessage) {
        return new Document("_id", newMessage.getId().id())
                .append("from_locale", LocaleFormatter.toDocument(newMessage.from_locale))
                .append("createdAt", newMessage.createdAt)
                .append("owner", newMessage.ownerId.id())
                .append("messageType", newMessage.type.name())
                .append("send_type", newMessage.sendType.name())
                .append("target_card", PhoneCard.toDocument(newMessage.targetCard))
                .append("communication_type", newMessage.communicationType.name())
                .append("fee", newMessage.fee);

    }

    public static MessageRecord buildFromDocument(Document document) {
        MessageRecord messageRecord = new MessageRecord();
        messageRecord.id = new EntityId(document.getString("_id"));
        messageRecord.from_locale = LocaleFormatter.buildFromDocument((Document) document.get("from_locale"));
        messageRecord.targetCard = PhoneCard.buildFromDocument((Document) document.get("target_card"));
        messageRecord.createdAt = document.getLong("createdAt");
        messageRecord.ownerId = new EntityId(document.getString("owner"));
        messageRecord.type = Type.valueOf(document.getString("messageType"));
        messageRecord.sendType = SendType.valueOf(document.getString("send_type"));
        messageRecord.communicationType = CommunicationType.valueOf(document.getString("communication_type"));
        messageRecord.fee = document.getDouble("fee");
        return messageRecord;

    }

    public enum Type {SMS, MMS}

    public enum SendType {SENDER, RECEIVER}

}
