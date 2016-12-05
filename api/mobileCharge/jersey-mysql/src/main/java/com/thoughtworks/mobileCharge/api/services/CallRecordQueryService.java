package com.thoughtworks.mobileCharge.api.services;

import com.thoughtworks.mobileCharge.domain.PaginatedList;
import com.thoughtworks.mobileCharge.domain.user.CallRecord;
import com.thoughtworks.mobileCharge.domain.user.User;

/**
 * Created by pzzheng on 11/29/16.
 */
public interface CallRecordQueryService {
    PaginatedList<CallRecord> findAllOf(User user, int month);
}
