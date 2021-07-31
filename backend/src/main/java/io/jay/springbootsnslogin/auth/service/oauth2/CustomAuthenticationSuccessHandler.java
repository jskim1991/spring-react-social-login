package io.jay.springbootsnslogin.auth.service.oauth2;

import io.jay.springbootsnslogin.auth.domain.PrincipalDetails;
import io.jay.springbootsnslogin.auth.token.TokenProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    private final TokenProvider tokenProvider;

    public CustomAuthenticationSuccessHandler(TokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        String accessToken = tokenProvider.createAccessToken(String.valueOf(principalDetails.getUser().getId()));
        Cookie tokenCookie = new Cookie("token", accessToken);
        tokenCookie.setPath("/");
        response.addCookie(tokenCookie);
        response.sendRedirect("http://localhost:3000/login/callback");
    }
}
