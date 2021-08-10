package com.foodgrid.common.unit;

import com.foodgrid.common.security.configuration.BeanConfiguration;
import com.foodgrid.common.security.configuration.SecurityConfiguration;
import com.foodgrid.common.security.filter.CORSFilter;
import com.foodgrid.common.security.filter.RequestFilter;
import com.foodgrid.common.security.implementation.UserDetailsServiceImplementation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.RestTemplate;

@SpringBootTest
@AutoConfigureWebTestClient
class SecurityConfigurationTests {

    @Autowired
    private SecurityConfiguration securityConfiguration;

    @MockBean
    private RequestFilter requestFilter;

    @MockBean
    private BeanConfiguration passwordConfig;

    @MockBean
    private CORSFilter corsFilter;

    @MockBean
    @Qualifier("internal")
    private RestTemplate internalRestTemplate;

    @MockBean
    @Qualifier("external")
    private RestTemplate externalRestTemplate;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @MockBean
    private DaoAuthenticationProvider daoAuthenticationProvider;

    @Test
    void testSecurityConfigurationTestsUserDetailsService() {
        Assertions.assertEquals(UserDetailsServiceImplementation.class, securityConfiguration.userDetailsService().getClass());
    }

    @Test
    void testSecurityConfigurationTestsAuthenticationManagerBean() {
        try {
            Assertions.assertNotNull(securityConfiguration.authenticationManagerBean().getClass());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void testSecurityConfigurationTestsDaoAuthenticationProvider() {
        Assertions.assertNotNull(securityConfiguration.daoAuthenticationProvider());
    }

}
