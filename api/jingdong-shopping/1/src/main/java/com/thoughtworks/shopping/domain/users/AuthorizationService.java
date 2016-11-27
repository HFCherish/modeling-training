package com.thoughtworks.shopping.domain.users;

/**
 * Created by pzzheng on 11/23/16.
 */
public interface AuthorizationService {
    boolean checkCanWriteProductOf(User user);

    boolean checkCanAccessOrder(User user);
}
