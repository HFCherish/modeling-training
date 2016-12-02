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
    protected final Type type;
    protected final SendType sendType;
    private final Long createdAt;
    private EntityId id;
    private User owner;

    public MessageRecord(User owner, Locale from_locale, PhoneCard targetCard, Type type, SendType sendType, Long createdAt) {
        super();
        this.id = new EntityId(IdGenerator.next());
        this.owner = owner;
        this.from_locale = from_locale;
        this.target = targetCard;
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
            put("target", target.toRefJson(routes));
            put("communication_type", communicationType.name());
            put("fee", fee);
            put("links", asList(
                    routes.linkMap("self", routes.messageRecordUrl(owner.getId().id(), id.id()).getPath())
            ));
        }};
    }

    @Override
    public Map<String, Object> toJson(Routes routes) {
        return toRefJson(routes);
    }

    public enum Type {SMS, MMS}

    public enum SendType {SENDER, RECEIVER}

    public static class ChargeBalance {
        long freeNumbers;
        double unitPrice;

        public ChargeBalance() {
        }

        public void addFreeNumber(long increment) {
            freeNumbers -= increment;
        }
    }

    public static class MessageChargeType {
        CommunicationType communicationType;
        Type type;
        SendType sendType;

        public MessageChargeType(CommunicationType communicationType, Type type, SendType sendType) {
            this.communicationType = communicationType;
            this.type = type;
            this.sendType = sendType;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            MessageChargeType that = (MessageChargeType) o;

            if (communicationType != that.communicationType) return false;
            if (type != that.type) return false;
            return sendType == that.sendType;

        }

        @Override
        public int hashCode() {
            int result = communicationType != null ? communicationType.hashCode() : 0;
            result = 31 * result + (type != null ? type.hashCode() : 0);
            result = 31 * result + (sendType != null ? sendType.hashCode() : 0);
            return result;
        }
    }
}
