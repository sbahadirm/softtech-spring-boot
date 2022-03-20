package com.softtech.softtechspringboot.app.kafka.dto;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author Bahadır Memiş
 * @since 1.0.0
 */
@Data
public class KafkaMessage {

    private Long id;
    private String message;
    private LocalDateTime dateTime;
}
