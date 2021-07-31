package io.jay.springbootsnslogin.auth.domain.oauth2;

import java.util.Map;

public class KakaoUser extends OAuth2UserInfo {

    public KakaoUser(Map<String, Object> attributes) {
        super(attributes);
    }

    @Override
    public String getUsername() {
        return attributes.get("id").toString();
    }
}
