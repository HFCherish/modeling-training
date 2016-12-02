package com.thoughtworks.mobileCharge.domain.user;

import com.thoughtworks.mobileCharge.domain.EntityId;
import com.thoughtworks.mobileCharge.infrastructure.records.Record;
import com.thoughtworks.mobileCharge.util.IdGenerator;
import com.thoughtworks.mobileCharge.api.jersey.Routes;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static com.thoughtworks.mobileCharge.util.LocaleFormatter.getCityAndCountry;
import static java.util.Arrays.asList;

/**
 * Created by pzzheng on 11/29/16.
 */
public class User implements Record {
    private final String idCard;
    private final EntityId id;
    private Balance balance;
    protected PhoneCard phoneCard;

    public User(String idCard, Balance balance, PhoneCard phoneCard) {
        this.id = new EntityId(IdGenerator.next());
        this.idCard = idCard;
        this.balance = balance;
        this.phoneCard = phoneCard;
    }

    public EntityId getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        return id != null ? id.equals(user.id) : user.id == null;

    }

    public Balance getBalance() {
        return balance;
    }

    public MessageRecord saveMessage(MessageRecord record){
        return null;
    }

    public CallRecord saveCallRecord(CallRecord callRecord){
        return null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public Map<String, Object> toRefJson(Routes routes) {
        return new HashMap(){{
            put("id", id.id());
            put("phone_card", phoneCard.toRefJson(routes));
            put("ID_Card", idCard);
            put("links", asList(
                routes.linkMap("self", routes.userUrl(id.id()).getPath())
            ));
        }};
    }

    @Override
    public Map<String, Object> toJson(Routes routes) {
        return toRefJson(routes);
    }

    public DataAccessRecord saveDataAccess(DataAccessRecord record) {
        return null;
    }
}
