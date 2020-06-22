package com.example.demo.listener;

import com.example.demo.api.SpaceShip;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageType;
import org.springframework.stereotype.Service;

import javax.jms.ConnectionFactory;

@Service
@Slf4j
@Configuration
public class Listeners {
    @JmsListener(destination = "myqueue2")
    public void receiveMessage(String message) {
        log.info("Received text message: {}", message);
    }

    @JmsListener(destination = "spaceships", containerFactory = "shipFactory")
    public void receiveShip(SpaceShip ship) {
        log.info("Received: {}", ship);
    }

    @Bean("shipFactory")
    public JmsListenerContainerFactory<?> myFactory(ConnectionFactory connectionFactory,
                                                    DefaultJmsListenerContainerFactoryConfigurer configurer
    ) {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        // This provides all boot's default to this factory, including the message converter
        configurer.configure(factory, connectionFactory);
        // You could still override some of Boot's default if necessary.
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        converter.setTargetType(MessageType.TEXT);
        converter.setTypeIdPropertyName("mytypeid");
        factory.setMessageConverter(converter);
        return factory;
    }

}
