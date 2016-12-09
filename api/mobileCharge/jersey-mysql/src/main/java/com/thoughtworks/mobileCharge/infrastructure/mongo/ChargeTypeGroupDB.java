package com.thoughtworks.mobileCharge.infrastructure.mongo;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.thoughtworks.mobileCharge.domain.user.ChargeTypeGroup;
import com.thoughtworks.mobileCharge.infrastructure.mappers.ChargeTypeGroupMapper;
import org.bson.Document;

import javax.inject.Inject;
import java.util.Optional;

/**
 * Created by pzzheng on 12/9/16.
 */
public class ChargeTypeGroupDB implements ChargeTypeGroupMapper {
    private MongoCollection<Document> chargeTypeGroups;

    @Inject
    public ChargeTypeGroupDB(MongoDatabase db) {
        chargeTypeGroups = db.getCollection("charge_type_groups");
    }

    @Override
    public Optional<ChargeTypeGroup> findById(String id) {
        Document document = chargeTypeGroups.find(Filters.eq("_id", id)).first();
        return Optional.ofNullable(ChargeTypeGroup.buildFromDocument(document));
    }
}
