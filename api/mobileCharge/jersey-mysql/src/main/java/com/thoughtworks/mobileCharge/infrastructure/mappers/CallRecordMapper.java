package com.thoughtworks.mobileCharge.infrastructure.mappers;

import com.thoughtworks.mobileCharge.domain.user.CallRecord;
import com.thoughtworks.mobileCharge.domain.user.User;

import java.util.List;

/**
 * Created by pzzheng on 12/9/16.
 */
public interface CallRecordMapper {
    CallRecord saveCallRecord(CallRecord callRecord);

    long countOf(User user, int month);

    List<CallRecord> findAllOf(User user, int month, int page, int perPage);
}
