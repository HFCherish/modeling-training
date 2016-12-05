package com.thoughtworks.mobileCharge.api.services;

import com.thoughtworks.mobileCharge.domain.PaginatedList;
import com.thoughtworks.mobileCharge.domain.user.MessageRecord;
import com.thoughtworks.mobileCharge.domain.user.User;

/**
 * Created by pzzheng on 12/2/16.
 */
public interface MessageRecordQueryService {
    PaginatedList<MessageRecord> findAllOf(User user, int month);
}
