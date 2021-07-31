package io.jay.springbootsnslogin.config;

import io.jay.springbootsnslogin.auth.service.oauth2.CustomAuthenticationFailureHandler;
import io.jay.springbootsnslogin.auth.service.oauth2.CustomAuthenticationSuccessHandler;
import io.jay.springbootsnslogin.auth.service.oauth2.CustomOAuth2UserService;
import io.jay.springbootsnslogin.auth.filter.JwtRequestFilter;
import io.jay.springbootsnslogin.auth.token.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.header.writers.frameoptions.WhiteListedAllowFromStrategy;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;

import java.util.Arrays;

@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final CustomOAuth2UserService customOAuth2UserService;
    private final CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;
    private final CustomAuthenticationFailureHandler customAuthenticationFailureHandler;

    private final TokenProvider tokenProvider;
    private final UserDetailsService userDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf()
                .ignoringAntMatchers("/h2-console/**")
                .disable();

        /* for h2-console */
        http
                .headers()
                .addHeaderWriter(
                        new XFrameOptionsHeaderWriter(
                                new WhiteListedAllowFromStrategy(Arrays.asList("localhost"))
                        )
                )
                .frameOptions().sameOrigin();

        /* endpoint access control */
        http
                .authorizeRequests()
                .antMatchers("/h2-console/**", "/auth/**", "/oauth2/callback/kakao/**").permitAll()
                .anyRequest().authenticated();

        /* disable session when using jwt */
        http
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        /* oauth2 login */
        http
                .oauth2Login()
                .userInfoEndpoint()
                .userService(customOAuth2UserService)
                .and()
                .successHandler(customAuthenticationSuccessHandler)
                .failureHandler(customAuthenticationFailureHandler);

        http
                .addFilterBefore(new JwtRequestFilter(userDetailsService, tokenProvider), UsernamePasswordAuthenticationFilter.class);
    }
}
