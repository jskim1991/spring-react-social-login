package io.jay.springbootsnslogin.auth.token;

public interface TokenProvider {
    String createAccessToken(String id);

    String getUserId(String token);
}
