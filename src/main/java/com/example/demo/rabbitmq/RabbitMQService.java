package com.example.demo.rabbitmq;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
@RequiredArgsConstructor
@Slf4j
public class RabbitMQService {
    private final AmqpTemplate rabbitTemplate;
    private final AmqpAdmin rabbitAdmin;

    @PostConstruct
    private void createStuff() {
        Exchange myexchange = ExchangeBuilder.directExchange("myrabbitexchange")
                .build();
        Queue queue = QueueBuilder.nonDurable("myrabbitqueue").build();
        rabbitAdmin.declareExchange(myexchange);
        rabbitAdmin.declareQueue(queue);
        rabbitAdmin.declareBinding(new Binding("myrabbitqueue"
                , Binding.DestinationType.QUEUE
                , "myrabbitexchange", "", null));

        rabbitTemplate.convertAndSend("myrabbitexchange", "", "This is a message....");

    }

    @RabbitListener(queues = "myrabbitqueue")
    public void messageReceived(String message) {
        log.info("message recieved: {]", message);
    }
}
