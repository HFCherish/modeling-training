package com.thoughtworks.mobileCharge.infrastructure.mappers;

import com.thoughtworks.mobileCharge.domain.test.MyTest;

import java.util.Optional;

/**
 * Created by pzzheng on 12/7/16.
 */
public interface MyTestMapper {
    Optional<MyTest> findById(String id);

    MyTest save(MyTest myTestObj);
}
