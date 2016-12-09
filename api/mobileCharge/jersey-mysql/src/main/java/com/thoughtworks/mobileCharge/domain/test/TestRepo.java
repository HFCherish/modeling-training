package com.thoughtworks.mobileCharge.domain.test;

import java.util.Optional;

/**
 * Created by pzzheng on 12/7/16.
 */
public interface TestRepo {
    MyTest save(MyTest myTestObj);

    Optional<MyTest> getById(String id);
}
