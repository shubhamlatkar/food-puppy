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
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@SpringBootTest(classes = {JwtTokenUtility.class})
@AutoConfigureWebTestClient
class JwtTokenUtilityTests {

    @Autowired
    private JwtTokenUtility jwtTokenUtility;
    

    @Test
    void testJwtTokenUtilityTests() throws NoSuchAlgorithmException {
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
//        ResponseEntity<String> response
//                = restTemplate.getForEntity("https://keygen.io/api.php?name=sha512", String.class);
//        var secret = response.getBody();

//        int leftLimit = 48; // numeral '0'
//        int rightLimit = 122; // letter 'z'
//        int targetStringLength = 500;
//        Random random = new Random();
//
//        String generatedString = random.ints(leftLimit, rightLimit + 1)
//                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
//                .limit(targetStringLength)
//                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
//                .toString();
//
//        String secretString = Base64.getEncoder().encodeToString(generatedString.getBytes(StandardCharsets.UTF_8));

        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
        byte[] salt = new byte[500];
        sr.nextBytes(salt);

        jwtTokenUtility.setSecret(Base64.getEncoder().encodeToString(salt));

        var token = jwtTokenUtility.generateToken(userDetails);

        Assertions.assertNotNull(token);
        Assertions.assertNotNull(jwtTokenUtility.getUsernameFromToken(token));
        Assertions.assertTrue(jwtTokenUtility.validateToken(token, userDetails));
        Assertions.assertFalse(jwtTokenUtility.isTokenExpired(token));
    }
}
