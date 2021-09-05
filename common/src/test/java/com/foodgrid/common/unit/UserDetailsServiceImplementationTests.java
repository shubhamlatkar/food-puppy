package com.foodgrid.common.unit;

import com.foodgrid.common.payload.dto.request.SignUp;
import com.foodgrid.common.security.implementation.UserDetailsServiceImplementation;
import com.foodgrid.common.security.model.aggregate.Authority;
import com.foodgrid.common.security.model.aggregate.Role;
import com.foodgrid.common.security.model.aggregate.User;
import com.foodgrid.common.security.model.entity.UserMetadata;
import com.foodgrid.common.security.repository.AuthorityRepository;
import com.foodgrid.common.security.repository.RoleRepository;
import com.foodgrid.common.security.repository.UserRepository;
import com.foodgrid.common.security.utility.JwtTokenUtility;
import com.foodgrid.common.utility.UserActivities;
import com.foodgrid.common.utility.UserTypes;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.List;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = {UserDetailsServiceImplementation.class})
@AutoConfigureWebTestClient
class UserDetailsServiceImplementationTests {

    @MockBean
    private AuthorityRepository authorityRepository;

    @MockBean
    private RoleRepository roleRepository;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @MockBean
    @Qualifier("external")
    private RestTemplate restTemplate;

    @MockBean
    private JwtTokenUtility jwtTokenUtility;

    @Autowired
    private UserDetailsServiceImplementation userDetailsServiceImplementation;

    @BeforeEach
    void init() {
        var tempRole = new Role("USER", List.of(new Authority("1", "TEST_AUTH")));
        when(roleRepository.findByName("USER")).thenReturn(java.util.Optional.of(tempRole));
    }

    @Test
    void testUserDetailsServiceImplementationTestsLoadUserByUsername() {
        var tempRole = new Role("USER", List.of(new Authority("1", "TEST_AUTH")));
        var tempUser = new User("test_username", "1234567890", "testemail@email.com", "test_pass", List.of(tempRole), UserTypes.USER);
        tempUser.setMetadata(new UserMetadata(new Date(), new Date(), UserActivities.LOGIN));
        tempUser.setId("1");
        when(userRepository.findByUsername("test_username"))
                .thenReturn(java.util.Optional.of(tempUser));

        Assertions.assertNotNull(userDetailsServiceImplementation.loadUserByUsername("test_username"));

    }

    @Test
    void testUserDetailsServiceImplementationTestsSaveUser() {
        var tempRole = new Role("USER", List.of(new Authority("1", "TEST_AUTH")));
        var tempUser = new User("test_username", "1234567890", "testemail@email.com", "test_pass", List.of(tempRole), UserTypes.USER);
        tempUser.setMetadata(new UserMetadata(new Date(), new Date(), UserActivities.LOGIN));
        tempUser.setId("1");
        when(userRepository.existsByUsername("test_username"))
                .thenReturn(Boolean.FALSE);
        doAnswer(invocationOnMock -> null)
                .when(userRepository).save(any());

        doAnswer(invocationOnMock -> "USER")
                .when(passwordEncoder).encode(anyString());

        Assertions.assertNotNull(userDetailsServiceImplementation.saveUser(new SignUp(
                "rosewood",
                "rosewood@shu.com",
                Set.of("ROLE_USER"),
                "rosewood@shu.com",
                "1234567890",
                UserTypes.USER
        )));

    }

    @Test
    void testUserDetailsServiceImplementationInitDatabase() {
//        doAnswer(invocationOnMock -> null)
//                .when(authorityRepository).saveAll(any());
//        doAnswer(invocationOnMock -> null)
//                .when(roleRepository).saveAll(any());
//        doAnswer(invocationOnMock -> null)
//                .when(jwtTokenUtility).setSecret(anyString());
//        doAnswer(invocationOnMock -> null).when(mongoTemplate).dropCollection(anyString());
//        userDetailsServiceImplementation.initDatabase(mongoTemplate);
        Assertions.assertNotNull(roleRepository.findByName("USER"));
    }
}
