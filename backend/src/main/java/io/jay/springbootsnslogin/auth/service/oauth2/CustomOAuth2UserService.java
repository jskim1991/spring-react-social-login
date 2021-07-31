package io.jay.springbootsnslogin.auth.service.oauth2;

import io.jay.springbootsnslogin.auth.store.UserStore;
import io.jay.springbootsnslogin.auth.domain.*;
import io.jay.springbootsnslogin.auth.domain.oauth2.*;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequestEntityConverter;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestOperations;

import java.util.Collections;
import java.util.Map;

import static io.jay.springbootsnslogin.auth.domain.oauth2.Provider.*;

@Service
public class CustomOAuth2UserService implements OAuth2UserService {

    private final RestOperations restOperations;
    private final UserStore userStore;

    public CustomOAuth2UserService(RestOperations restOperations, UserStore userStore) {
        this.restOperations = restOperations;
        this.userStore = userStore;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        Map<String, Object> attributes = getUserAttributes(userRequest);
        String clientName = userRequest.getClientRegistration().getClientName();

        OAuth2UserInfo userInfo = buildUserInfo(attributes, clientName);
        User user = userStore.retrieveUserByUsernameAndProvider(userInfo.getUsername(), clientName);
        if (user == null) {
            User newUser = User.builder()
                    .username(userInfo.getUsername())
                    .roles(Collections.singleton(RoleType.ROLE_USER))
                    .provider(clientName)
                    .build();
            User savedUser = userStore.addNewUser(newUser);
            return new PrincipalDetails(savedUser, attributes);
        } else {
            return new PrincipalDetails(user, attributes);
        }
    }

    private OAuth2UserInfo buildUserInfo(Map<String, Object> attributes, String clientName) {
        Provider provider = valueOf(clientName);
        switch (provider) {
            case Kakao:
                return new KakaoUser(attributes);
            case Naver:
                return new NaverUser(attributes);
            case Google:
                return new GoogleUser(attributes);
            default:
                throw new IllegalArgumentException("Unsupported OAuth2.0 Login");
        }
    }

    private Map<String, Object> getUserAttributes(OAuth2UserRequest userRequest) {
        RequestEntity<?> request = new OAuth2UserRequestEntityConverter().convert(userRequest);
        ResponseEntity<Map<String, Object>> responseEntity = getResponse(userRequest, request);
        Map<String, Object> attributes = responseEntity.getBody();
        return attributes;
    }

    private ResponseEntity<Map<String, Object>> getResponse(OAuth2UserRequest userRequest, RequestEntity<?> request) {
        return this.restOperations.exchange(request, new ParameterizedTypeReference<>() {
        });
    }
}