package io.jay.springbootsnslogin.controller;

import io.jay.springbootsnslogin.auth.domain.PrincipalDetails;
import io.jay.springbootsnslogin.auth.domain.RoleType;
import io.jay.springbootsnslogin.auth.domain.User;
import io.jay.springbootsnslogin.util.JsonUtil;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

public class UserControllerTests {

    @Test
    void test_getUser_returnsUser() throws Exception {
        MockMvc mockMvc = MockMvcBuilders
                .standaloneSetup(new UserController())
                .setCustomArgumentResolvers(new PrincipalDetailsArgumentResolver())
                .build();


        String returnedUser = mockMvc.perform(get("/user"))
                .andReturn()
                .getResponse()
                .getContentAsString();
        User user = JsonUtil.fromJson(returnedUser, User.class);

        assertThat(user.getId(), equalTo(1L));
        assertThat(user.getUsername(), equalTo("some name"));
        assertThat(user.getProvider(), equalTo("some sns provider"));
        assertThat(user.getRoles().contains(RoleType.ROLE_USER), equalTo(true));
    }
}
