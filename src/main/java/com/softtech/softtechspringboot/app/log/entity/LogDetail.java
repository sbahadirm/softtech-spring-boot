package com.softtech.softtechspringboot.app.log.entity;

import com.softtech.softtechspringboot.app.gen.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

/**
 * @author Bahadır Memiş
 * @since 1.0.0
 */
@Entity
@Table(name = "LOG_DETAIL")
@Getter
@Setter
public class LogDetail extends BaseEntity {

    @GeneratedValue(generator = "LogDetail")
    @SequenceGenerator(name = "LogDetail" , sequenceName = "LOG_DETAIL_ID_SEQ")
    @Id
    private Long id;

    private String message;
    private String details;
    private Date dateTime;

}
