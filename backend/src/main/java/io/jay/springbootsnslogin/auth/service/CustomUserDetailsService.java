package io.jay.springbootsnslogin.auth.service;

import io.jay.springbootsnslogin.auth.domain.PrincipalDetails;
import io.jay.springbootsnslogin.auth.domain.User;
import io.jay.springbootsnslogin.auth.store.UserStore;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserStore userStore;

    public CustomUserDetailsService(UserStore userStore) {
        this.userStore = userStore;
    }

    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        User user = userStore.retrieveUser(Long.parseLong(id));
        return user != null ? new PrincipalDetails(user) : null;
    }
}
