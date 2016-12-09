package com.thoughtworks.mobileCharge.domain.user;

import com.thoughtworks.mobileCharge.api.jersey.Routes;
import com.thoughtworks.mobileCharge.domain.ChargeType;
import com.thoughtworks.mobileCharge.domain.EntityId;
import com.thoughtworks.mobileCharge.infrastructure.records.Record;
import com.thoughtworks.mobileCharge.util.IdGenerator;
import com.thoughtworks.mobileCharge.util.LocaleFormatter;
import org.bson.Document;
import org.joda.time.DateTime;
import org.joda.time.Duration;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static java.util.Arrays.asList;

/**
 * Created by pzzheng on 12/2/16.
 */
public class DataAccessRecord extends CommunicationRecord implements Record {
    protected EntityId ownerId;
    protected Locale fromLocale;
    protected String targetConsumer;
    protected WebType webType;
    protected ChargeType chargeType;
    protected Long data;
    protected Long createdAt;
    protected EntityId id;

    private DataAccessRecord() {
    }

    public DataAccessRecord(User owner, Locale fromLocale, String targetConsumer, WebType webType, ChargeType chargeType, Long data, Long createdAt) {
        this.id = new EntityId(IdGenerator.next());
        this.ownerId = owner.getId();
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
                    routes.linkMap("self", routes.dataAccessRecordUrl(ownerId.id(), id.id()).getPath())
            ));
        }};

    }

    @Override
    public Map<String, Object> toJson(Routes routes) {
        return toRefJson(routes);
    }

    public static Document toDocument(DataAccessRecord newDataAccess) {
        return new Document("_id", newDataAccess.getId().id())
                .append("from_locale", LocaleFormatter.toDocument(newDataAccess.fromLocale))
                .append("data", newDataAccess.data)
                .append("createdAt", newDataAccess.createdAt)
                .append("owner", newDataAccess.ownerId.id())
                .append("web_type", newDataAccess.webType.name())
                .append("charge_type", newDataAccess.chargeType.name())
                .append("target_consumer", newDataAccess.targetConsumer)
                .append("communication_type", newDataAccess.communicationType.name())
                .append("fee", newDataAccess.fee);
    }

    public static DataAccessRecord buildFromDocument(Document document) {
        DataAccessRecord dataAccessRecord = new DataAccessRecord();
        dataAccessRecord.id = new EntityId(document.getString("_id"));
        dataAccessRecord.fromLocale = LocaleFormatter.buildFromDocument((Document)document.get("from_locale"));
        dataAccessRecord.targetConsumer = document.getString("target_consumer");
        dataAccessRecord.createdAt = document.getLong("createdAt");
        dataAccessRecord.data = document.getLong("data");
        dataAccessRecord.ownerId = new EntityId(document.getString("owner"));
        dataAccessRecord.webType = WebType.valueOf(document.getString("web_type"));
        dataAccessRecord.chargeType = ChargeType.valueOf(document.getString("charge_type"));
        dataAccessRecord.communicationType = CommunicationType.valueOf(document.getString("communication_type"));
        dataAccessRecord.fee = document.getDouble("fee");
        return dataAccessRecord;

    }

    public enum WebType {G4, G3, G2 }

}
