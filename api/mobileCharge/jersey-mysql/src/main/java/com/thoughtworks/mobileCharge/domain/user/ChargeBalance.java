package com.thoughtworks.mobileCharge.domain.user;

import org.bson.Document;

/**
 * Created by pzzheng on 12/2/16.
 */
public class ChargeBalance {
    long freeNumber;
    double unitPrice;

    public ChargeBalance() {
    }

    public void addFreeNumber(long increment) {
        freeNumber -= increment;
    }

    public static ChargeBalance buildFromDocument(Document document) {
        ChargeBalance chargeBalance = new ChargeBalance();
        chargeBalance.freeNumber = document.getLong("free_number");
        chargeBalance.unitPrice = document.getDouble("unit_price");
        return chargeBalance;
    }
}
