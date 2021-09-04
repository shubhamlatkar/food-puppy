package com.foodgrid.configuration;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureWebTestClient
class ConfigurationApplicationTests {

    @MockBean
    private SecretKey secretKey;

    @Autowired
    private ConfigurationApplication configurationApplication;

    @Test
    @DisplayName("Tests getSecret method of ConfigurationApplication")
    void getSecret() {
        when(secretKey.getSecret()).thenReturn("test");
        doAnswer(invocationOnMock -> null).when(secretKey).setSecret(any());
        Assertions.assertNotNull(configurationApplication.getSecret());
    }

    @Test
    @DisplayName("Tests main method of ConfigurationApplication")
    void main() {
        var args = new String[]{"1"};
        ConfigurationApplication.main(args);
        Assertions.assertNotNull(args);
    }
}
