package com.foodgrid.common.security.filter;

import com.foodgrid.common.security.implementation.UserDetailsImplementation;
import com.foodgrid.common.security.implementation.UserDetailsServiceImplementation;
import com.foodgrid.common.security.model.aggregate.User;
import com.foodgrid.common.security.repository.UserRepository;
import com.foodgrid.common.security.utility.JwtTokenUtility;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class RequestFilter extends OncePerRequestFilter {

    private final JwtTokenUtility jwtTokenUtility;
    private final UserDetailsServiceImplementation userDetailsService;
    private final UserRepository userRepository;


    @Autowired
    public RequestFilter(JwtTokenUtility jwtTokenUtil, UserDetailsServiceImplementation userDetailsService, UserRepository userRepository) {
        this.jwtTokenUtility = jwtTokenUtil;
        this.userDetailsService = userDetailsService;
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {

        final String authorization = httpServletRequest.getHeader("Authorization");

        System.out.println(httpServletRequest.getRequestURL().toString());

        Cookie[] cookies = httpServletRequest.getCookies();
        String cookieJWT = null;
        if (cookies != null) {
            cookieJWT = Arrays.stream(cookies)
                    .map(Cookie::getValue).findFirst().orElse(null);
        }

        String username = null;
        String jwt = null;
        try {
            if (authorization != null && authorization.startsWith("Bearer ")) {
                jwt = authorization.substring(7);
                username = jwtTokenUtility.getUsernameFromToken(jwt);
            } else if (cookieJWT != null) {
                username = jwtTokenUtility.getUsernameFromToken(cookieJWT);
            }
        } catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException e) {
            httpServletResponse.sendError(HttpServletResponse.SC_FORBIDDEN, "Unauthorized...");
            return;
        }

        User user = null;
        if (username != null)
            user = userRepository.findByUsername(username).orElse(null);
        String finalJwt = jwt;
        if (user != null) {
            List<String> activeTokens = user.getActiveTokens();
            if (!activeTokens.contains(finalJwt))
                httpServletResponse.sendError(HttpServletResponse.SC_FORBIDDEN, "Unauthorized...");
            if (httpServletRequest.getRequestURL().toString().contains("/logmeout"))
                userRepository.save(user.removeToken(finalJwt));
            if (httpServletRequest.getRequestURL().toString().contains("/logoutall"))
                userRepository.save(user.setActiveTokens(new ArrayList<>()));

        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetailsImplementation userDetails = (UserDetailsImplementation) userDetailsService.loadUserByUsername(username);
            var usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                    userDetails,
                    null,
                    userDetails.getAuthorities()
            );
            usernamePasswordAuthenticationToken
                    .setDetails(
                            new WebAuthenticationDetailsSource().buildDetails(httpServletRequest)
                    );
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
