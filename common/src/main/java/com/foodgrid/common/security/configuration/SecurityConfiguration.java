package com.foodgrid.common.security.configuration;

import com.foodgrid.common.exception.exceptions.InternalServerErrorException;
import com.foodgrid.common.security.filter.CORSFilter;
import com.foodgrid.common.security.filter.RequestFilter;
import com.foodgrid.common.security.implementation.UserDetailsServiceImplementation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Slf4j
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final RequestFilter requestFilter;
    private final BeanConfiguration passwordConfig;
    private final CORSFilter corsFilter;

    @Autowired
    public SecurityConfiguration(RequestFilter requestFilter, BeanConfiguration passwordConfig, CORSFilter corsFilter) {
        this.requestFilter = requestFilter;
        this.passwordConfig = passwordConfig;
        this.corsFilter = corsFilter;
    }

    @Override
    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsServiceImplementation();
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/**/login", "/**/signup", "/**/notification/**").permitAll()
                .antMatchers(HttpMethod.GET, "/", "/**/member/**", "/**/static/**", "/**/exception", "/**/public/**/").permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterBefore(corsFilter, ChannelProcessingFilter.class);
        http.addFilterBefore(requestFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        try {
            auth.authenticationProvider(daoAuthenticationProvider());
        } catch (Exception e) {
            log.error("Internal server error daoAuthenticationProvider failed", new InternalServerErrorException("daoAuthenticationProvider failed"));
        }
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        var provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService());
        provider.setPasswordEncoder(passwordConfig.passwordEncoder());
        return provider;
    }

}