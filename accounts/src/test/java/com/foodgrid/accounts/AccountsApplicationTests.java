package com.foodgrid.accounts;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@AutoConfigureWebTestClient
class AccountsApplicationTests {

    @Autowired
    private AccountsApplication accountsApplication;

    @Test
    @DisplayName("Tests main method of AccountsApplication")
    void main() {
        var args = new String[]{"1"};
        AccountsApplication.main(args);
        Assertions.assertNotNull(args);
    }

    @Test
    @DisplayName("Tests redirect method of AccountApplication")
    void redirect() {
        Assertions.assertNotNull(accountsApplication.defaultGet());
    }
}
