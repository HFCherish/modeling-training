package com.thoughtworks.mobileCharge.infrastructure.mappers;

import com.thoughtworks.mobileCharge.domain.user.DataAccessRecord;
import com.thoughtworks.mobileCharge.domain.user.User;

import java.util.List;

/**
 * Created by pzzheng on 12/9/16.
 */
public interface DataAccessRecordMapper {
    DataAccessRecord saveDataAccessRecord(DataAccessRecord newDataAccess);

    long countOf(User user, int month);

    List<DataAccessRecord> findAllOf(User user, int month, int page, int perPage);
}
