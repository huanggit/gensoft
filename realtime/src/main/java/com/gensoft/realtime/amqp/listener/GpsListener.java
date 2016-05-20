package com.gensoft.realtime.amqp.listener;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * Created by alan on 16-5-19.
 */
@Component
public class GpsListener {

    private static final String gpsQueue = "gps";

    @Bean
    Queue queue() {
        return new Queue(gpsQueue, true);
    }

    @RabbitListener(queues = gpsQueue)
    public void processGps(String content) {
        System.out.println(gpsQueue+": "+content);
    }

}
