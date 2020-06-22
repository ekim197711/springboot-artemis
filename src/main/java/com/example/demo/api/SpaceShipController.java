package com.example.demo.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.jms.TextMessage;

@RestController
@RequiredArgsConstructor
@RequestMapping("/spaceship")
@Slf4j
public class SpaceShipController {
    private final JmsTemplate jmsTemplate;

    @PostMapping("/")
    public String sendShip(@RequestBody SpaceShip ship) {
        log.info("Send this message to jms queue: " + ship);
        jmsTemplate.send("spaceships", s -> {
            try {
                TextMessage tm = s.createTextMessage(new ObjectMapper().writeValueAsString(ship));
                tm.setJMSType(SpaceShip.class.getTypeName());
                tm.setStringProperty("mytypeid", SpaceShip.class.getTypeName());
                return tm;
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        });
        return "Ship sent";
    }

}
