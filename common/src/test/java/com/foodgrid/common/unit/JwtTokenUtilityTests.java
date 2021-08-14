package com.foodgrid.common.unit;

import com.foodgrid.common.security.implementation.UserDetailsImplementation;
import com.foodgrid.common.security.model.aggregate.Authority;
import com.foodgrid.common.security.model.aggregate.Role;
import com.foodgrid.common.security.model.aggregate.User;
import com.foodgrid.common.security.model.entity.UserMetadata;
import com.foodgrid.common.security.utility.JwtTokenUtility;
import com.foodgrid.common.utility.UserActivities;
import com.foodgrid.common.utility.UserTypes;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@SpringBootTest
@AutoConfigureWebTestClient
class JwtTokenUtilityTests {

    @Autowired
    private JwtTokenUtility jwtTokenUtility;

    @Autowired
    @Qualifier("external")
    private RestTemplate restTemplate;

    @Test
    void testJwtTokenUtilityTests() {
        var tempRole = new Role("USER", List.of(new Authority("1", "TEST_AUTH")));
        var tempUser = new User("test_username", "1234567890", "testemail@email.com", "test_pass", List.of(tempRole), UserTypes.USER);
        tempUser.setMetadata(new UserMetadata(new Date(), new Date(), UserActivities.LOGIN));
        tempUser.setId("1");
        tempUser.addToken("test_token");

        Set<GrantedAuthority> authorities = tempUser.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName()))
                .collect(Collectors.toSet());

        tempUser.getRoles().forEach(role -> {
            for (Authority authority : role.getAuthorities()) {
                authorities.add(new SimpleGrantedAuthority(authority.getName()));
            }
        });

        var userDetails = new UserDetailsImplementation(
                tempUser.getUsername(),
                tempUser.getPassword(),
                authorities,
                tempUser.getEmail(),
                tempUser.getId()
        );
        ResponseEntity<String> response
                = restTemplate.getForEntity("https://keygen.io/api.php?name=sha512", String.class);
        var secret = response.getBody();

        jwtTokenUtility.setSecret(secret);

        var token = jwtTokenUtility.generateToken(userDetails);

        Assertions.assertNotNull(token);
        Assertions.assertNotNull(jwtTokenUtility.getUsernameFromToken(token));
        Assertions.assertTrue(jwtTokenUtility.validateToken(token, userDetails));
        Assertions.assertFalse(jwtTokenUtility.isTokenExpired(token));
    }
}
