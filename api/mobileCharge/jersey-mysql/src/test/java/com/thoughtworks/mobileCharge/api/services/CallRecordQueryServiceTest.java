package com.thoughtworks.mobileCharge.api.services;

import com.thoughtworks.mobileCharge.domain.PaginatedList;
import com.thoughtworks.mobileCharge.domain.user.CallRecord;
import com.thoughtworks.mobileCharge.domain.user.User;
import com.thoughtworks.mobileCharge.domain.user.UserRepo;
import com.thoughtworks.mobileCharge.support.DatabaseTestRunner;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;

import static com.thoughtworks.mobileCharge.support.TestHelper.getCallRecord;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by pzzheng on 12/9/16.
 */
@RunWith(DatabaseTestRunner.class)
public class CallRecordQueryServiceTest {
    @Inject
    UserRepo userRepo;
    private User user;

    @Before
    public void setUp() {
        user = userRepo.findAll().get(0);
    }

    @Test
    public void should_get_all_call_records() {
        CallRecord toSave = getCallRecord(user, new DateTime());
        user.saveCallRecord(toSave);
        PaginatedList<CallRecord> allCallRecords = user.findAllCallRecords(0);
        assertThat(allCallRecords.size(), is(1l));
    }

    @Test
    public void should_get_all_call_records_by_month() {
        user.saveCallRecord(getCallRecord(user, new DateTime(2016, 1,1,1,1)));
        user.saveCallRecord(getCallRecord(user, new DateTime(2016, 2,1,1,1)));

        assertThat(user.findAllCallRecords(0).size(), is(2l));

        assertThat(user.findAllCallRecords(1).size(), is(1l));
        assertThat(user.findAllCallRecords(2).size(), is(1l));
    }

}