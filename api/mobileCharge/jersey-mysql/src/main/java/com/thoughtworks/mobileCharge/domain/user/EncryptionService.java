package com.thoughtworks.mobileCharge.domain.user;

public interface EncryptionService {
    String encrypt(String password);
    boolean check(String checkPassword, String realPassword);
}
