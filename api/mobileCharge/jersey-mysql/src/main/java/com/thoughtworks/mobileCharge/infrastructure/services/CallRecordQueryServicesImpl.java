package com.thoughtworks.mobileCharge.infrastructure.services;

import com.thoughtworks.mobileCharge.api.services.CallRecordQueryService;
import com.thoughtworks.mobileCharge.domain.PaginatedList;
import com.thoughtworks.mobileCharge.domain.user.CallRecord;
import com.thoughtworks.mobileCharge.domain.user.User;
import com.thoughtworks.mobileCharge.infrastructure.mappers.CallRecordMapper;

import javax.inject.Inject;

/**
 * Created by pzzheng on 12/9/16.
 */
public class CallRecordQueryServicesImpl implements CallRecordQueryService{

    @Inject
    CallRecordMapper callRecordMapper;

    @Override
    public PaginatedList<CallRecord> findAllOf(User user, int month) {
        return new PaginatedList<>(callRecordMapper.countOf(user, month), (page, perPage) -> callRecordMapper.findAllOf(user, month, page, perPage));
    }
}
