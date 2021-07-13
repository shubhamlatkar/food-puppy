package com.foodgrid.common.security.implementation;


import com.foodgrid.common.payload.dto.request.SignUp;
import com.foodgrid.common.security.configuration.BeanConfiguration;
import com.foodgrid.common.security.model.aggregate.Authority;
import com.foodgrid.common.security.model.aggregate.Role;
import com.foodgrid.common.security.model.aggregate.User;
import com.foodgrid.common.security.repository.AuthorityRepository;
import com.foodgrid.common.security.repository.RoleRepository;
import com.foodgrid.common.security.repository.UserRepository;
import com.foodgrid.common.security.utility.JwtTokenUtility;
import com.foodgrid.common.security.utility.UserTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.foodgrid.common.security.utility.Authorities.*;
import static com.foodgrid.common.security.utility.Roles.*;


@Service
public class UserDetailsServiceImplementation implements UserDetailsService {


    @Autowired
    AuthorityRepository authorityRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BeanConfiguration passwordConfig;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private JwtTokenUtility jwtTokenUtility;

    @Value("${spring.cloud.config.uri}")
    private String configUri;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));

        Set<GrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName()))
                .collect(Collectors.toSet());

        user.getRoles().forEach(role -> {
            for (Authority authority : role.getAuthorities()) {
                authorities.add(new SimpleGrantedAuthority(authority.getName()));
            }
        });

        return UserDetailsImplementation.build(user);
    }

    public Boolean saveUser(SignUp signUp) {
        if (userRepository.existsByUsername(signUp.getUsername()) || userRepository.existsByEmail(signUp.getEmail()))
            return false;

        List<Role> roles = signUp
                .getRoles()
                .stream()
                .map(role -> roleRepository.findByName(role.substring(5)).orElse(null))
                .collect(Collectors.toList());

        userRepository.save(
                new User(
                        signUp.getUsername(),
                        signUp.getPhone(),
                        signUp.getEmail(),
                        passwordConfig.passwordEncoder().encode(signUp.getPassword()),
                        roles,
                        UserTypes.USER
                )
        );
        return true;

    }

    public void initDatabase(MongoTemplate mongoTemplate) {
        ResponseEntity<String> response
                = restTemplate.getForEntity(configUri + "/api/v1/secret/", String.class);
//                = restTemplate.getForEntity("https://keygen.io/api.php?name=sha512", String.class);
        jwtTokenUtility.setSecret(response.getBody());

        mongoTemplate.dropCollection(Authority.class);
        mongoTemplate.dropCollection(Role.class);
        mongoTemplate.dropCollection(User.class);
        authorityRepository.saveAll(
                new ArrayList<>(Arrays.asList(
                        new Authority(USER_WRITE.getValue()),
                        new Authority(USER_READ.getValue()),
                        new Authority(RESTAURANT_READ.getValue()),
                        new Authority(RESTAURANT_WRITE.getValue()),
                        new Authority(SERVICE_READ.getValue()),
                        new Authority(SERVICE_WRITE.getValue())
                ))
        );
        roleRepository.saveAll(new ArrayList<>(Arrays.asList(
                new Role(USER.name(), new ArrayList<>(Arrays.asList(
                        authorityRepository.findByName(USER_READ.getValue()).orElse(null),
                        authorityRepository.findByName(USER_WRITE.getValue()).orElse(null)
                ))),
                new Role(RESTAURANT.name(), new ArrayList<>(Arrays.asList(
                        authorityRepository.findByName(RESTAURANT_READ.getValue()).orElse(null),
                        authorityRepository.findByName(RESTAURANT_WRITE.getValue()).orElse(null)
                ))),
                new Role(SERVICE.name(), new ArrayList<>(Arrays.asList(
                        authorityRepository.findByName(SERVICE_READ.getValue()).orElse(null),
                        authorityRepository.findByName(SERVICE_WRITE.getValue()).orElse(null)
                ))),
                new Role(ADMIN.name(), new ArrayList<>(Arrays.asList(
                        authorityRepository.findByName(SERVICE_READ.getValue()).orElse(null),
                        authorityRepository.findByName(SERVICE_WRITE.getValue()).orElse(null),
                        authorityRepository.findByName(RESTAURANT_READ.getValue()).orElse(null),
                        authorityRepository.findByName(RESTAURANT_WRITE.getValue()).orElse(null),
                        authorityRepository.findByName(USER_WRITE.getValue()).orElse(null),
                        authorityRepository.findByName(USER_READ.getValue()).orElse(null)
                )))
        )));
    }

}