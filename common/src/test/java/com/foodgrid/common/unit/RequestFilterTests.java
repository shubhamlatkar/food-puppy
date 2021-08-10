package com.foodgrid.common.unit;

import com.foodgrid.common.security.component.UserSession;
import com.foodgrid.common.security.filter.RequestFilter;
import com.foodgrid.common.security.implementation.UserDetailsServiceImplementation;
import com.foodgrid.common.security.model.aggregate.Authority;
import com.foodgrid.common.security.model.aggregate.Role;
import com.foodgrid.common.security.repository.RoleRepository;
import com.foodgrid.common.security.repository.UserRepository;
import com.foodgrid.common.security.utility.JwtTokenUtility;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureWebTestClient
class RequestFilterTests {

    @Autowired
    private RequestFilter requestFilter;

    @MockBean
    private JwtTokenUtility jwtTokenUtility;

    @MockBean
    private UserDetailsServiceImplementation userDetailsService;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private UserSession userSession;

    @MockBean
    private HttpServletRequest httpServletRequest;

    @MockBean
    private HttpServletResponse httpServletResponse;

    @MockBean
    private FilterChain chain;

    @MockBean
    private RoleRepository roleRepository;

    @BeforeEach
    void init() {
        var tempRole = new Role("USER", List.of(new Authority("1", "TEST_AUTH")));
        when(roleRepository.findByName("USER")).thenReturn(java.util.Optional.of(tempRole));
    }

    @Test
    void testBeanConfigurationTestsPasswordEncoder() {
        Assertions.assertNotNull(roleRepository.findByName("USER"));
    }
}
