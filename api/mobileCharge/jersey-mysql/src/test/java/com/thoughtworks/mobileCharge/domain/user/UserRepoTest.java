package com.thoughtworks.mobileCharge.domain.user;

import com.thoughtworks.mobileCharge.support.DatabaseTestRunner;
import com.thoughtworks.mobileCharge.util.IdGenerator;
import com.thoughtworks.mobileCharge.util.LocaleFormatter;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.*;

/**
 * Created by pzzheng on 12/9/16.
 */
@RunWith(DatabaseTestRunner.class)
public class UserRepoTest {

    @Inject
    UserRepo userRepo;

    @Test
    public void should_can_find_user_with_right_basic_info() {
        List<User> allUsers = userRepo.findAll();
        assertThat(allUsers.size(), is(2));

        User user0 = allUsers.get(0);
        Optional<User> user0ById = userRepo.findById(user0.getId().id());
        assertThat(user0ById.isPresent(), is(true));
        assertThat(user0ById.get().id, is(user0.id));
        assertThat(user0ById.get().idCard, is(user0.idCard));

        PhoneCard phoneCard = user0ById.get().phoneCard;
        assertThat(phoneCard.phoneNumber.matches("[0-9]+"), is(true));
        assertThat(LocaleFormatter.getCityAndCountry(phoneCard.locale), is("beijing-China"));
    }
}