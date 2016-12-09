package com.thoughtworks.mobileCharge.infrastructure.mappers;

import com.thoughtworks.mobileCharge.domain.user.DataAccessRecord;

/**
 * Created by pzzheng on 12/9/16.
 */
public interface DataAccessRecordMapper {
    DataAccessRecord saveDataAccessRecord(DataAccessRecord newDataAccess);

}
