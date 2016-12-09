package com.thoughtworks.mobileCharge.infrastructure.services;

import com.thoughtworks.mobileCharge.api.services.DataAccessRecordQueryService;
import com.thoughtworks.mobileCharge.domain.PaginatedList;
import com.thoughtworks.mobileCharge.domain.user.DataAccessRecord;
import com.thoughtworks.mobileCharge.domain.user.User;
import com.thoughtworks.mobileCharge.infrastructure.mappers.DataAccessRecordMapper;

import javax.inject.Inject;

/**
 * Created by pzzheng on 12/9/16.
 */
public class DataAccessRecordQueryServiceImpl implements DataAccessRecordQueryService {

    @Inject
    DataAccessRecordMapper dataAccessRecordMapper;

    @Override
    public PaginatedList<DataAccessRecord> findAllOf(User user, int month) {
        return new PaginatedList<>(dataAccessRecordMapper.countOf(user, month), (page, perPage) -> dataAccessRecordMapper.findAllOf(user, month, page, perPage));
    }
}
