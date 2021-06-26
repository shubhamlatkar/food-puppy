package com.foodgrid.configuration;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("singleton")
public class SecretKey {

    private String secret;

    public SecretKey() {
    }

    public SecretKey(String secret) {
        this.secret = secret;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    @Override
    public String toString() {
        return "SecretKey{" +
                "secret='" + secret + '\'' +
                '}';
    }
}
