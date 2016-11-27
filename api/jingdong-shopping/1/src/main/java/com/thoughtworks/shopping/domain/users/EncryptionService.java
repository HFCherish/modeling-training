package com.thoughtworks.shopping.domain.users;

public interface EncryptionService {
    String encrypt(String password);
    boolean check(String checkPassword, String realPassword);
}
