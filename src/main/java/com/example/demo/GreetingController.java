package com.example.demo;

import lombok.RequiredArgsConstructor;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.SimpleMessageConverter;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/greeting")
public class GreetingController {
    private final JmsTemplate jmsTemplate;

    @PostMapping("/")
    public String sendMessage(@RequestBody String message) {
        SimpleMessageConverter converter = new SimpleMessageConverter();
        jmsTemplate.send("myqueue", s -> s.createTextMessage(message));
        return "Annnnd done...";
    }

}
