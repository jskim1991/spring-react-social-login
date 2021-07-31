package io.jay.springbootsnslogin.auth.token;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Date;
import java.util.List;

@Component
public class JwtTokenProvider implements TokenProvider {

    private final long MINUTES = 60 * 1000L;
    private final long ACCESS_TOKEN_VALID_TIME = 10 * MINUTES;
    private final long REFRESH_TOKEN_VALID_TIME = 30 * MINUTES;
    private JwtSecretKey secretKey;

    public JwtTokenProvider(JwtSecretKey secretKey) {
        this.secretKey = secretKey;
    }

    @Override
    public String createAccessToken(String id) {
        return createToken(id, ACCESS_TOKEN_VALID_TIME);
    }

    @Override
    public String getUserId(String token) {
        Jws<Claims> claimsJws = Jwts.parser()
                .setSigningKey(Base64.getEncoder().encodeToString(secretKey.getSecretKeyAsBytes()))
                .parseClaimsJws(token);
        Claims claims = claimsJws.getBody();
        return String.valueOf(claims.get("my-service-user-id"));
    }

    public String createRefreshToken(String email, List<String> roles) {
        return createToken(email, REFRESH_TOKEN_VALID_TIME);
    }

    private String createToken(String id, long validTime) {
        Claims claims = Jwts.claims();
        claims.put("my-service-user-id", id);
        Date now = new Date();
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + validTime))
                .signWith(SignatureAlgorithm.HS256, Base64.getEncoder().encodeToString(secretKey.getSecretKeyAsBytes()))
                .compact();
    }
}
