package com.thoughtworks.mobileCharge.infrastructure.repositories;

import com.thoughtworks.mobileCharge.domain.user.User;
import com.thoughtworks.mobileCharge.domain.user.UserRepo;
import com.thoughtworks.mobileCharge.infrastructure.mappers.UserMapper;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

/**
 * Created by pzzheng on 12/8/16.
 */
public class UserRepoImpl implements UserRepo {
    @Inject
    UserMapper userMapper;

    @Override
    public Optional<User> findById(String id) {
        return userMapper.findById(id);
    }

    @Override
    public List<User> findAll() {
        return userMapper.findAll();
    }
}
