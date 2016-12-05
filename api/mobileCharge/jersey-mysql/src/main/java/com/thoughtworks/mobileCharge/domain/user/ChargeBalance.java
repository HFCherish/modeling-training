package com.thoughtworks.mobileCharge.domain.user;

/**
 * Created by pzzheng on 12/2/16.
 */
public class ChargeBalance {
    long freeNumbers;
    double unitPrice;

    public ChargeBalance() {
    }

    public void addFreeNumber(long increment) {
        freeNumbers -= increment;
    }
}
