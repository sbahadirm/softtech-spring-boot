package com.softtech.softtechspringboot.app.kafka.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Bahadır Memiş
 * @since 1.0.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LogMessage implements Serializable {

    private Long id ;
    private String message;
    private String description;
    private Date dateTime;
}
