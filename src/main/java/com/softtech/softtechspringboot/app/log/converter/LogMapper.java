package com.softtech.softtechspringboot.app.log.converter;

import com.softtech.softtechspringboot.app.kafka.dto.LogMessage;
import com.softtech.softtechspringboot.app.log.entity.LogDetail;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 * @author Bahadır Memiş
 * @since 1.0.0
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface LogMapper {

    LogMapper INSTANCE = Mappers.getMapper(LogMapper.class);

    @Mapping(target = "details", source = "description")
    LogDetail convertToLogDetail(LogMessage logMessage);
}
