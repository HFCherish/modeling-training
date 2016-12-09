package com.thoughtworks.mobileCharge.infrastructure.mappers;

import com.thoughtworks.mobileCharge.domain.user.ChargeTypeGroup;

import java.util.Optional;

/**
 * Created by pzzheng on 12/9/16.
 */
public interface ChargeTypeGroupMapper {
    Optional<ChargeTypeGroup> findById(String id);

}
