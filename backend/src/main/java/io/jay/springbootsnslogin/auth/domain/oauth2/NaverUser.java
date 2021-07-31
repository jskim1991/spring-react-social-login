package io.jay.springbootsnslogin.auth.domain.oauth2;

import java.util.Map;

public class NaverUser extends OAuth2UserInfo {

    public NaverUser(Map<String, Object> attributes) {
        super(attributes);
    }

    @Override
    public String getUsername() {
        Map<String, Object> userAttributes = (Map<String, Object>) attributes.get("response");
        return userAttributes.get("id").toString();
    }
}
