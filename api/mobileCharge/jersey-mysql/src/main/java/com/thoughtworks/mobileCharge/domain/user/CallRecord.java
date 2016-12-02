package com.thoughtworks.mobileCharge.domain.user;

import com.thoughtworks.mobileCharge.domain.EntityId;
import com.thoughtworks.mobileCharge.infrastructure.records.Record;
import com.thoughtworks.mobileCharge.util.IdGenerator;
import com.thoughtworks.mobileCharge.api.jersey.Routes;
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

    private final Locale from_locale;
    protected final Duration duration;
    private final EntityId id;
    private final DateTime start;
    private User owner;
    protected CallType callType;

    private PhoneCard targetCard;

    public CallRecord(Locale from_locale, User owner, DateTime start, Duration duration, CallType callType, PhoneCard targetCard) {
        this.id = new EntityId(IdGenerator.next());
        this.from_locale = from_locale;
        this.owner = owner;
        this.start = start;
        this.duration = duration;
        this.callType = callType;
        this.targetCard = targetCard;
        this.communicationType = CommunicationType.typeOf(from_locale, targetCard.locale);
        this.fee = owner.getBalance().charge(this, Balance.ChargeStrategies.callCharge());
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
            put("call_type", callType.name());
            put("communication_type", communicationType.name());
            put("fee", 0.0);
            put("links", asList(
                    routes.linkMap("self", routes.callRecordsUrl(owner.getId().id(), id.id()).getPath())
            ));
        }};
    }

    @Override
    public Map<String, Object> toJson(Routes routes) {
        return toRefJson(routes);
    }

    public enum CallType {
        CALLER, CALLEE
    }

}
