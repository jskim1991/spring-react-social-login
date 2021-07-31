package io.jay.springbootsnslogin.auth.domain.oauth2;

import java.util.Map;

public class GoogleUser extends OAuth2UserInfo {
    public GoogleUser(Map<String, Object> attributes) {
        super(attributes);
    }

    @Override
    public String getUsername() {
        return attributes.get("sub").toString();
    }
}
