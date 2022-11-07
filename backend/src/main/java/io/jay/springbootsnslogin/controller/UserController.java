package io.jay.springbootsnslogin.controller;

import io.jay.springbootsnslogin.auth.domain.PrincipalDetails;
import io.jay.springbootsnslogin.auth.domain.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @GetMapping("/user")
    public User getUser(@AuthenticationPrincipal PrincipalDetails principalDetails) {
        return principalDetails.getUser();
    }
}
