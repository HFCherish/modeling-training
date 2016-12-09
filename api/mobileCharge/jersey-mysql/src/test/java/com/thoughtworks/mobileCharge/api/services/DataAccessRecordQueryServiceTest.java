package com.thoughtworks.mobileCharge.api.services;

import com.thoughtworks.mobileCharge.domain.PaginatedList;
import com.thoughtworks.mobileCharge.domain.user.DataAccessRecord;
import com.thoughtworks.mobileCharge.domain.user.User;
import com.thoughtworks.mobileCharge.domain.user.UserRepo;
import com.thoughtworks.mobileCharge.support.DatabaseTestRunner;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;

import static com.thoughtworks.mobileCharge.support.TestHelper.getDataAccessRecord;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by pzzheng on 12/9/16.
 */
@RunWith(DatabaseTestRunner.class)
public class DataAccessRecordQueryServiceTest {
    @Inject
    DataAccessRecordQueryService dataAccessRecordQueryService;
    @Inject
    UserRepo userRepo;
    private User user;

    @Before
    public void setUp() {
        user = userRepo.findAll().get(0);
    }

    @Test
    public void should_get_all_data_access_records() {
        DataAccessRecord toSave = getDataAccessRecord(user, new DateTime());
        user.saveDataAccess(toSave);
        PaginatedList<DataAccessRecord> dataAccessRecords = dataAccessRecordQueryService.findAllOf(user, 0);
        assertThat(dataAccessRecords.size(), is(1l));
    }

    @Test
    public void should_get_all_data_access_records_by_month() {
        user.saveDataAccess(getDataAccessRecord(user, new DateTime(2016, 1, 1, 1, 1)));
        user.saveDataAccess(getDataAccessRecord(user, new DateTime(2016, 2, 1, 1, 1)));

        assertThat(dataAccessRecordQueryService.findAllOf(user, 0).size(), is(2l));

        assertThat(dataAccessRecordQueryService.findAllOf(user, 1).size(), is(1l));
        assertThat(dataAccessRecordQueryService.findAllOf(user, 2).size(), is(1l));
    }

}