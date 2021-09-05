package com.foodgrid.user.command;

import com.foodgrid.common.payload.dto.response.GetItemResponse;
import com.foodgrid.user.command.external.service.RestService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = {RestService.class})
@AutoConfigureWebTestClient
class RestServiceTests {

    @MockBean
    @Qualifier("internal")
    private RestTemplate restTemplate;

    @Autowired
    private RestService restService;

    @Test
    @DisplayName("Tests getItemShort method of RestService")
    void getItemShort() {
        when(restTemplate.getForEntity(anyString(), any())).thenReturn(ResponseEntity.ok(new GetItemResponse("1", "test", 12.23)));
        Assertions.assertNotNull(restService.getItemShort("1", "1"));
    }
}
