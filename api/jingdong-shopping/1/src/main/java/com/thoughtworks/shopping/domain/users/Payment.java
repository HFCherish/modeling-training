package com.thoughtworks.shopping.domain.users;

import com.thoughtworks.shopping.domain.EntityId;
import com.thoughtworks.shopping.util.IdGenerator;

/**
 * Created by pzzheng on 11/25/16.
 */
public class Payment {
    String account;
    String password;
    EntityId id;
    Order order;


    public Payment(String account, String password, Order order) {
        this.id = new EntityId(IdGenerator.next());
        this.account = account;
        this.password = password;
        this.order = order;
    }
}
