package io.jay.springbootsnslogin.auth.store;

import io.jay.springbootsnslogin.auth.domain.User;

public interface UserStore {

    User retrieveUser(Long id);

    User retrieveUserByUsernameAndProvider(String username, String provider);

    User addNewUser(User newUser);
}
