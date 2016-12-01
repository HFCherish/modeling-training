package com.thoughtworks.mobileCharge.domain.user;

import com.thoughtworks.mobileCharge.domain.PaginatedList;

/**
 * Created by pzzheng on 11/29/16.
 */
public interface CallRecordRepo {
    CallRecord save(CallRecord callRecord);

    PaginatedList<CallRecord> findAllOf(User user, int month);
//    List<CallRecord> findAllOf(User user);
}
