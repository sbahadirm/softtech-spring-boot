package com.softtech.softtechspringboot.app.kafka.producer;

import com.softtech.softtechspringboot.app.kafka.dto.LogMessage;
import com.softtech.softtechspringboot.app.log.service.LogService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Bahadır Memiş
 * @since 1.0.0
 */
@RestController
@RequestMapping("/kafka-messages")
@RequiredArgsConstructor
public class KafkaMessageController {

    private final LogService logService;

    @PostMapping
    public void sendMessage(@RequestBody LogMessage logMessage){

        System.out.println("starting to produce");

        logService.log(logMessage);

        System.out.println("message sent from producer");
    }
}
