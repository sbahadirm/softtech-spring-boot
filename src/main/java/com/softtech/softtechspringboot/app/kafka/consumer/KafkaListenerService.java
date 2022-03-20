package com.softtech.softtechspringboot.app.kafka.consumer;

import com.softtech.softtechspringboot.app.kafka.dto.LogMessage;
import com.softtech.softtechspringboot.app.log.converter.LogMapper;
import com.softtech.softtechspringboot.app.log.entity.LogDetail;
import com.softtech.softtechspringboot.app.log.service.entityservice.LogDetailEntityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Bahadır Memiş
 * @since 1.0.0
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaListenerService {

    private final LogDetailEntityService logDetailEntityService;

    @KafkaListener(
            topics = "${softtech.kafka.topic}",
            groupId = "${softtech.kafka.group-id}"
    )
    public void listen(@Payload LogMessage logMessage){

        log.info("Message received by consumer... " + logMessage.getMessage());

        saveLogToDb(logMessage);
    }

    @Transactional
    public void saveLogToDb(LogMessage logMessage) {
        LogDetail logDetail = LogMapper.INSTANCE.convertToLogDetail(logMessage);

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("**********************************");

        logDetail = logDetailEntityService.save(logDetail);

        System.out.println("end");
    }
}
