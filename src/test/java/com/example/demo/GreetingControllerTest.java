package com.example.demo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class GreetingControllerTest {

    @Autowired
    GreetingController greetingController;

    @Test
    void sendMessage() {
        String s = greetingController.sendMessage("Test 1 2 3");
        Assertions.assertEquals("Annnnd done...", s);
    }
}