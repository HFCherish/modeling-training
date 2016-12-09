package com.thoughtworks.mobileCharge.domain.user;

import com.thoughtworks.mobileCharge.domain.EntityId;
import com.thoughtworks.mobileCharge.infrastructure.records.Record;
import com.thoughtworks.mobileCharge.util.IdGenerator;
import com.thoughtworks.mobileCharge.api.jersey.Routes;
import com.thoughtworks.mobileCharge.util.LocaleFormatter;
import org.bson.Document;
import org.joda.time.DateTime;
import org.joda.time.Duration;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static com.thoughtworks.mobileCharge.util.LocaleFormatter.getCityAndCountry;
import static java.util.Arrays.asList;


/**
 * Created by pzzheng on 11/29/16.
 */
public class CallRecord extends CommunicationRecord implements Record {

    private Locale from_locale;
    protected Duration duration;
    private EntityId id;
    DateTime start;
    EntityId ownerId;
    protected CallType callType;

    PhoneCard targetCard;

    private CallRecord() {
    }

    public CallRecord(Locale from_locale, User owner, DateTime start, Duration duration, CallType callType, PhoneCard targetCard) {
        this.id = new EntityId(IdGenerator.next());
        this.from_locale = from_locale;
        this.ownerId = owner.id;
        this.start = start;
        this.duration = duration;
        this.callType = callType;
        this.targetCard = targetCard;
        this.communicationType = CommunicationType.typeOf(from_locale, targetCard.locale);
//        this.fee = owner.getBalance().charge(this, Balance.ChargeStrategies.callCharge());
    }

    public EntityId getId() {
        return id;
    }

    @Override
    public Map<String, Object> toRefJson(Routes routes) {
        return new HashMap() {{
            put("id", id.id());
            put("from_locale", getCityAndCountry(from_locale));
            put("target", targetCard.toRefJson(routes));
            put("createdAt", start);
            put("duration", duration.getMillis());
            put("call_type", callType.name());
            put("communication_type", communicationType.name());
            put("fee", fee);
            put("links", asList(
                    routes.linkMap("self", routes.callRecordsUrl(ownerId.id(), id.id()).getPath())
            ));
        }};
    }

    @Override
    public Map<String, Object> toJson(Routes routes) {
        return toRefJson(routes);
    }

    public static Document toDocument(CallRecord callRecord) {
        return new Document("_id", callRecord.getId().id())
                .append("from_locale", LocaleFormatter.toDocument(callRecord.from_locale))
                .append("duration", callRecord.duration.getMillis())
                .append("start", callRecord.start.getMillis())
                .append("owner", callRecord.ownerId.id())
                .append("call_type", callRecord.callType.name())
                .append("target_card", PhoneCard.toDocument(callRecord.targetCard))
                .append("communication_type", callRecord.communicationType.name())
                .append("fee", callRecord.fee);
    }

    public static CallRecord buildFromDocument(Document document) {
        CallRecord callRecord = new CallRecord();
        callRecord.id = new EntityId(document.getString("_id"));
        callRecord.from_locale = LocaleFormatter.buildFromDocument((Document) document.get("from_locale"));
        callRecord.duration = new Duration(document.getLong("duration"));
        callRecord.targetCard = PhoneCard.buildFromDocument((Document) document.get("target_card"));
        callRecord.start = new DateTime(document.getLong("start"));
        callRecord.ownerId = new EntityId(document.getString("owner"));
        callRecord.callType = CallType.valueOf(document.getString("call_type"));
        callRecord.communicationType = CommunicationType.valueOf(document.getString("communication_type"));
        callRecord.fee = document.getDouble("fee");
        return callRecord;
    }

    public enum CallType {
        CALLER, CALLEE
    }

}
