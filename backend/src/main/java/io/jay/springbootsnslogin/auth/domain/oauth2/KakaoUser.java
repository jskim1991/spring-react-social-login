package io.jay.springbootsnslogin.auth.domain.oauth2;

import java.util.Map;

public class KakaoUser extends OAuth2UserInfo {

    public KakaoUser(Map<String, Object> attributes) {
        super(attributes);
    }

    @Override
    public String getUserIdByProvider() {
        return attributes.get("id").toString();
    }

    @Override
    public String getUsername() {
        Map<String, Object> userAttributes = (Map<String, Object>) attributes.get("properties");
        return userAttributes.get("nickname").toString();
    }
}
