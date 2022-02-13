package com.softtech.softtechspringboot.app.cus.converter;

import com.softtech.softtechspringboot.app.cus.dto.CusCustomerDto;
import com.softtech.softtechspringboot.app.cus.dto.CusCustomerSaveRequestDto;
import com.softtech.softtechspringboot.app.cus.dto.CusCustomerUpdateRequestDto;
import com.softtech.softtechspringboot.app.cus.entity.CusCustomer;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author Bahadır Memiş
 * @since 1.0.0
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CusCustomerMapper {

    CusCustomerMapper INSTANCE = Mappers.getMapper(CusCustomerMapper.class);

    CusCustomer convertToCusCustomer(CusCustomerSaveRequestDto cusCustomerSaveRequestDto);

    CusCustomer convertToCusCustomer(CusCustomerUpdateRequestDto cusCustomerUpdateRequestDto);

    List<CusCustomerDto> convertToCusCustomerDtoList(List<CusCustomer> cusCustomerList);

    CusCustomerDto convertToCusCustomerDto(CusCustomer cusCustomer);
}
