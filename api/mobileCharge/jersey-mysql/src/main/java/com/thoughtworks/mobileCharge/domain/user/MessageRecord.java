package com.thoughtworks.mobileCharge.domain.user;

import com.thoughtworks.mobileCharge.api.jersey.Routes;
import com.thoughtworks.mobileCharge.domain.EntityId;
import com.thoughtworks.mobileCharge.infrastructure.records.Record;
import com.thoughtworks.mobileCharge.util.IdGenerator;
import com.thoughtworks.mobileCharge.util.LocaleFormatter;
import org.joda.time.DateTime;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static java.util.Arrays.asList;

/**
 * Created by pzzheng on 11/30/16.
 */
public class MessageRecord extends CommunicationRecord implements Record {
    private final Locale from_locale;
    private final PhoneCard target;
    private final Type type;
    private final SendType sendType;
    private final Long createdAt;
    private EntityId id;
    private User user;

    public MessageRecord(User user, Locale from_locale, PhoneCard targetCard, Type type, SendType sendType, Long createdAt) {
        this.id = new EntityId(IdGenerator.next());
        this.user = user;
        this.from_locale = from_locale;
        this.target = targetCard;
        this.type = type;
        this.sendType = sendType;
        this.createdAt = createdAt;

        this.communicationType = CommunicationType.typeOf(from_locale, targetCard.locale);
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
            put("target", target.toRefJson(routes));
            put("communication_type", communicationType.name());
            put("links", asList(
                    routes.linkMap("self", routes.messageRecordUrl(user.getId().id(), id.id()).getPath())
            ));
        }};
    }

    @Override
    public Map<String, Object> toJson(Routes routes) {
        return toRefJson(routes);
    }

    public enum Type {SMS, MMS}

    public enum SendType {SENDER, RECEIVER}
}
