package io.jay.springbootsnslogin.controller;

import io.jay.springbootsnslogin.auth.domain.PrincipalDetails;
import io.jay.springbootsnslogin.auth.domain.RoleType;
import io.jay.springbootsnslogin.auth.domain.User;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.Collections;

public class PrincipalDetailsArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().isAssignableFrom(PrincipalDetails.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        User user = User.builder()
                .id(1L)
                .username("some name")
                .provider("some sns provider")
                .roles(Collections.singleton(RoleType.ROLE_USER))
                .build();
        PrincipalDetails principalDetails = new PrincipalDetails(user);
        return principalDetails;
    }
}
