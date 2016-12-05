package com.thoughtworks.mobileCharge.domain.user;

import com.thoughtworks.mobileCharge.api.jersey.Routes;
import com.thoughtworks.mobileCharge.domain.ChargeType;
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
 * Created by pzzheng on 12/2/16.
 */
public class DataAccessRecord extends CommunicationRecord implements Record {
    private final User owner;
    private final Locale fromLocale;
    private final String targetConsumer;
    private final WebType webType;
    protected final ChargeType chargeType;
    protected final Long data;
    private final Long createdAt;
    private EntityId id;

    public DataAccessRecord(User owner, Locale fromLocale, String targetConsumer, WebType webType, ChargeType chargeType, Long data, Long createdAt) {
        this.id = new EntityId(IdGenerator.next());
        this.owner = owner;
        this.fromLocale = fromLocale;
        this.targetConsumer = targetConsumer;
        this.webType = webType;
        this.chargeType = chargeType;
        this.data = data;
        this.createdAt = createdAt;

        this.communicationType = CommunicationType.typeOf(fromLocale, owner.phoneCard.locale);
        this.fee = owner.getBalance().charge(this, Balance.ChargeStrategies.dataAccessCharge());
    }

    public EntityId getId() {
        return id;
    }

    @Override
    public Map<String, Object> toRefJson(Routes routes) {
        return new HashMap() {{
            put("id", id.id());
            put("charge_type", chargeType.name());
            put("web_type", webType.name());
            put("createdAt", new DateTime(createdAt));
            put("from_locale", LocaleFormatter.getCityAndCountry(fromLocale));
            put("data", data);
            put("target_consumer", targetConsumer);
            put("communication_type", communicationType.name());
            put("fee", fee);
            put("links", asList(
                    routes.linkMap("self", routes.dataAccessRecordUrl(owner.getId().id(), id.id()).getPath())
            ));
        }};

    }

    @Override
    public Map<String, Object> toJson(Routes routes) {
        return toRefJson(routes);
    }

    public enum WebType {G4, G3, G2 }

    public static class DataAccessChargeType {
        CommunicationType communicationType;

        public DataAccessChargeType(CommunicationType communicationType) {
            this.communicationType = communicationType;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            DataAccessChargeType that = (DataAccessChargeType) o;

            return communicationType == that.communicationType;

        }

        @Override
        public int hashCode() {
            return communicationType != null ? communicationType.hashCode() : 0;
        }
    }
}
