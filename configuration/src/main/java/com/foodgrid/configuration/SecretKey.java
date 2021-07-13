package com.foodgrid.configuration;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 *  @since 1.0
 *  @author shubhamlatkar
 *  @version 1.1
 */
@Component
@Scope("singleton")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SecretKey {
    private String secret;
}
