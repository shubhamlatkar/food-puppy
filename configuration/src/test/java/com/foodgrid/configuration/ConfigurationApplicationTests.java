package com.foodgrid.configuration;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Random;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureWebTestClient
class ConfigurationApplicationTests {

    @MockBean
    private Random random;

    @MockBean
    private SecretKey secretKey;

    @Autowired
    private ConfigurationApplication configurationApplication;

    @Test
    @DisplayName("Tests getSecret method of ConfigurationApplication")
    void getSecret() {
        when(secretKey.getSecret()).thenReturn("test");
        when(random.ints(anyInt(), anyInt())).thenReturn(new Random().ints(48, 123));
        doAnswer(invocationOnMock -> null).when(secretKey).setSecret(any());
        Assertions.assertNotNull(configurationApplication.getSecret());
    }
}
