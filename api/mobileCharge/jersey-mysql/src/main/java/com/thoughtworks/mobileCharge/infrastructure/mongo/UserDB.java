package com.thoughtworks.mobileCharge.infrastructure.mongo;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.thoughtworks.mobileCharge.domain.user.Balance;
import com.thoughtworks.mobileCharge.domain.user.User;
import com.thoughtworks.mobileCharge.infrastructure.mappers.UserMapper;
import org.bson.Document;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Projections.*;

/**
 * Created by pzzheng on 12/8/16.
 */
public class UserDB implements UserMapper {
    MongoCollection<Document> users;

    @Inject
    public UserDB(MongoDatabase db) {
        users = db.getCollection("users");
    }

    @Override
    public Optional<User> findById(String id) {
        Document document = users.find(Filters.eq("_id", id))
                .projection(fields(include("idCard", "phoneCard"))).first();
        return Optional.ofNullable(User.buildFromDocument(document));
    }

    @Override
    public List<User> findAll() {
        ArrayList<Document> documents = users.find()
                .projection(fields(include("idCard", "phoneCard"))).into(new ArrayList<>());
        return documents.stream().map(document -> User.buildFromDocument(document)).filter(user -> user!=null).collect(Collectors.toList());
    }

    @Override
    public Balance getBalanceOf(User user) {
        Document document = users.find(Filters.eq("_id", user.getId().id()))
                .projection(and(fields(include("balance")), fields(excludeId())))
                .first();
        return Balance.buildFromDocument((Document)document.get("balance"));
    }

}
