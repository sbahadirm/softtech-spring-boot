package com.softtech.softtechspringboot.app.kafka.producer;

import com.softtech.softtechspringboot.app.kafka.dto.KafkaMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * @author Bahadır Memiş
 * @since 1.0.0
 */
@RestController
@RequestMapping("/kafka-messages")
@RequiredArgsConstructor
public class KafkaMessageController {

    @Value("${softtech.kafka.topic}")
    private String topic;

    private final KafkaTemplate<String, KafkaMessage> kafkaTemplate;

    @PostMapping
    public void sendMessage(@RequestBody KafkaMessage kafkaMessage){

        System.out.println("starting to produce");

        for (int i = 0; i< 9999; i++){
//            String id = UUID.randomUUID().toString();
            String id = String.valueOf(i);
            kafkaMessage.setId(Long.valueOf(id));
            kafkaTemplate.send(topic, id, kafkaMessage);
        }

        System.out.println("message sent from producer");
    }
}
