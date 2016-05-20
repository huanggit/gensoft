package com.gensoft.realtime.amqp.sender;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by alan on 16-5-19.
 */
@Service
public class RabbitMqSender {

    @Autowired
    RabbitTemplate rabbitTemplate;

    public void send(String queueName, String message){
        rabbitTemplate.convertAndSend(queueName, message);
    }
}
