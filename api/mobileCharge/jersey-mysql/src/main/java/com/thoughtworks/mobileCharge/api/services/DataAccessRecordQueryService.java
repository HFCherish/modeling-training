package com.thoughtworks.mobileCharge.api.services;

import com.thoughtworks.mobileCharge.domain.PaginatedList;
import com.thoughtworks.mobileCharge.domain.user.DataAccessRecord;
import com.thoughtworks.mobileCharge.domain.user.User;

/**
 * Created by pzzheng on 12/2/16.
 */
public interface DataAccessRecordQueryService {
    PaginatedList<DataAccessRecord> findAllOf(User user, int month);
}
