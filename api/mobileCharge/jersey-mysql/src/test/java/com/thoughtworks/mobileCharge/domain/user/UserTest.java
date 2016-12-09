package com.thoughtworks.mobileCharge.domain.user;

import com.thoughtworks.mobileCharge.api.services.CallRecordQueryService;
import com.thoughtworks.mobileCharge.domain.PaginatedList;
import com.thoughtworks.mobileCharge.support.DatabaseTestRunner;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;

import java.util.Locale;

import static com.thoughtworks.mobileCharge.support.TestHelper.beijingLocale;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * Created by pzzheng on 12/9/16.
 */
@RunWith(DatabaseTestRunner.class)
public class UserTest {

    @Inject
    UserRepo userRepo;

    @Test
    public void should_save_and_get_that_call_record() {
        User user = userRepo.findAll().get(0);
        DateTime start = new DateTime();
        PhoneCard targetCard = new PhoneCard("12313131231", beijingLocale());
        CallRecord toSave = new CallRecord(beijingLocale(),
                user,
                start,
                new Duration(60),
                CallRecord.CallType.CALLER,
                targetCard);

        CallRecord saved = user.saveCallRecord(toSave);
        assertThat(saved.getId(), is(toSave.getId()));
        assertThat(saved.callType, is(CallRecord.CallType.CALLER));
        assertThat(saved.duration, is(new Duration(60)));
        assertThat(saved.communicationType, is(CommunicationRecord.CommunicationType.LOCAL));
        assertThat(saved.start, is(start));
        assertThat(saved.targetCard.phoneNumber, is(targetCard.phoneNumber));
        assertThat(saved.ownerId, is(user.getId()));
    }


}