package com.thoughtworks.mobileCharge.domain.user;

import com.thoughtworks.mobileCharge.domain.EntityId;
import com.thoughtworks.mobileCharge.domain.PaginatedList;
import com.thoughtworks.mobileCharge.infrastructure.mappers.CallRecordMapper;
import com.thoughtworks.mobileCharge.infrastructure.mappers.DataAccessRecordMapper;
import com.thoughtworks.mobileCharge.infrastructure.mappers.MessageRecordMapper;
import com.thoughtworks.mobileCharge.infrastructure.mappers.UserMapper;
import com.thoughtworks.mobileCharge.infrastructure.records.Record;
import com.thoughtworks.mobileCharge.infrastructure.util.SafetyInjector;
import com.thoughtworks.mobileCharge.util.IdGenerator;
import com.thoughtworks.mobileCharge.api.jersey.Routes;
import org.bson.Document;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;

import static java.util.Arrays.asList;

/**
 * Created by pzzheng on 11/29/16.
 */
public class User implements Record {
    protected String idCard;
    protected EntityId id;
    private Balance balance;
    protected PhoneCard phoneCard;

    @Inject
    CallRecordMapper callRecordMapper;

    @Inject
    MessageRecordMapper messageRecordMapper;

    @Inject
    DataAccessRecordMapper dataAccessRecordMapper;

    @Inject
    UserMapper userMapper;

    public User(String idCard, Balance balance, PhoneCard phoneCard) {
        this.id = new EntityId(IdGenerator.next());
        this.idCard = idCard;
        this.balance = balance;
        this.phoneCard = phoneCard;
    }

    private User() {}

    public static User buildFromDocument(Document document) {
        if(document.isEmpty())  return null;
        User user = SafetyInjector.injectMembers(new User());
        user.id = new EntityId(document.get("_id").toString());
        user.idCard = document.getString("idCard");
//        user.balance = Balance.buildFromDocument((Document)(document.get("balance")));
        user.phoneCard = PhoneCard.buildFromDocument((Document)(document.get("phoneCard")));
        return user;
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
        if(balance == null) {
            balance = userMapper.getBalanceOf(this);
        }
        return balance;
    }

    public MessageRecord saveMessage(MessageRecord newMessage) {
        return messageRecordMapper.saveMessage(newMessage);
    }

    public CallRecord saveCallRecord(CallRecord newCall) {
        return callRecordMapper.saveCallRecord(newCall);
    }

    public PaginatedList<CallRecord> findAllCallRecords(int month) {
        return new PaginatedList<>(callRecordMapper.countOf(this, month), (page, perPage) -> callRecordMapper.findAllOf(this, month, page, perPage));
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public Map<String, Object> toRefJson(Routes routes) {
        return new HashMap() {{
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

    public DataAccessRecord saveDataAccess(DataAccessRecord newDataAccess) {
        return dataAccessRecordMapper.saveDataAccessRecord(newDataAccess);
    }
}
