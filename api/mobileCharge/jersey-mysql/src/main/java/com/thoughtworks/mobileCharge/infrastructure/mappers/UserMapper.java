package com.thoughtworks.mobileCharge.infrastructure.mappers;

import com.thoughtworks.mobileCharge.domain.user.Balance;
import com.thoughtworks.mobileCharge.domain.user.User;

import java.util.List;
import java.util.Optional;

public interface UserMapper {
    Optional<User> findById(String id);

    List<User> findAll();

    Balance getBalanceOf(User user);
}
