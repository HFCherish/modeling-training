package com.thoughtworks.mobileCharge.domain.user;

import com.thoughtworks.mobileCharge.domain.EntityId;
import com.thoughtworks.mobileCharge.util.IdGenerator;

import java.util.Locale;

/**
 * Created by pzzheng on 11/30/16.
 */
public class MessageRecord extends CommunicationRecord {
    private final Locale from_locale;
    private final PhoneCard target;
    private final Type type;
    private final SendType sendType;
    private final Long createdAt;
    private EntityId id;

    public MessageRecord(Locale from_locale, PhoneCard target, Type type, SendType sendType, Long createdAt) {
        this.id = new EntityId(IdGenerator.next());
        this.from_locale = from_locale;
        this.target = target;
        this.type = type;
        this.sendType = sendType;
        this.createdAt = createdAt;
    }

    public EntityId getId() {
        return id;
    }

    public enum Type {SMS, MMS}

    public enum SendType {SENDER, RECEIVER}
}
