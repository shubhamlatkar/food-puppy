package com.foodgrid.common.unit;

import com.foodgrid.common.security.configuration.BeanConfiguration;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = {BeanConfiguration.class})
@AutoConfigureWebTestClient
class BeanConfigurationTests {

    @Autowired
    private BeanConfiguration beanConfiguration;

    @Test
    void testBeanConfigurationTestsPasswordEncoder() {
        Assertions.assertNotNull(beanConfiguration.passwordEncoder());
    }

    @Test
    void testBeanConfigurationTestsInternalRestTemplate() {
        Assertions.assertNotNull(beanConfiguration.internalRestTemplate());
    }

    @Test
    void testBeanConfigurationTestsExternalRestTemplate() {
        Assertions.assertNotNull(beanConfiguration.externalRestTemplate());
    }

}
