package com.example.demo.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/greeting")
@Slf4j
public class GreetingController {
    private final JmsTemplate jmsTemplate;

    @PostMapping("/")
    public String sendMessage(@RequestBody String message) {
        log.info("Send this message to jms queue: " + message);
        jmsTemplate.convertAndSend("myqueue2", message);
        return "Message sent " + message;
    }


}
