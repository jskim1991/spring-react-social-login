package io.jay.springbootsnslogin.auth.domain.oauth2;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Provider {
    Kakao("Kakao"),
    Naver("Naver"),
    Google("Google");

    private final String name;
}
