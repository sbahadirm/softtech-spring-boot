package com.softtech.softtechspringboot.app.kafka.consumer;

import com.softtech.softtechspringboot.app.kafka.dto.KafkaMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

/**
 * @author Bahadır Memiş
 * @since 1.0.0
 */
@Service
@Slf4j
public class KafkaListenerService {

    @KafkaListener(
            topics = "${softtech.kafka.topic}",
            groupId = "${softtech.kafka.group-id}"
    )
    public void listen(@Payload KafkaMessage kafkaMessage){

        log.info("Message received by consumer... " + kafkaMessage.getId());
    }
}
