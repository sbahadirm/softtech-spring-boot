package com.softtech.softtechspringboot.app.cus.controller;

import com.softtech.softtechspringboot.app.cus.dto.CusCustomerDto;
import com.softtech.softtechspringboot.app.cus.dto.CusCustomerSaveRequestDto;
import com.softtech.softtechspringboot.app.cus.dto.CusCustomerUpdateRequestDto;
import com.softtech.softtechspringboot.app.cus.service.CusCustomerService;
import com.softtech.softtechspringboot.app.gen.dto.RestResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Bahadır Memiş
 * @since 1.0.0
 */
@RestController
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor
public class CusCustomerController {

    private final CusCustomerService cusCustomerService;

    @Operation(tags = "Customer Controller", description = "Gets all customer.", summary = "All customer")
    @GetMapping
    public ResponseEntity findAll(){

        List<CusCustomerDto> cusCustomerDtoList = cusCustomerService.findAll();

        return ResponseEntity.ok(RestResponse.of(cusCustomerDtoList));
    }

    @Operation(tags = "Customer Controller")
    @GetMapping("/{id}")
    public ResponseEntity findById(@PathVariable Long id){

        CusCustomerDto cusCustomerDto = cusCustomerService.findById(id);

        return ResponseEntity.ok(RestResponse.of(cusCustomerDto));
    }

    @Operation(
            tags = "Customer Controller",
            description = "Creates new customer",
            summary = "creates new customer",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Customers",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            implementation = CusCustomerSaveRequestDto.class
                                    ),
                                    examples = {
                                            @ExampleObject(
                                                    name = "new foreign customer",
                                                    summary = "New Foreign Customer Example",
                                                    description = "Complete request with all available fields for foreign customer",
                                                    value = "{\n" +
                                                            "  \"name\": \"john\",\n" +
                                                            "  \"surname\": \"smith\",\n" +
                                                            "  \"identityNo\": 92345678901,\n" +
                                                            "  \"password\": \"J.s_1234\"\n" +
                                                            "}"
                                            ),
                                            @ExampleObject(
                                                    name = "new customer",
                                                    summary = "New Customer Example",
                                                    description = "Complete request with all available fields",
                                                    value = "{\n" +
                                                            "  \"name\": \"ali\",\n" +
                                                            "  \"surname\": \"veli\",\n" +
                                                            "  \"identityNo\": 12345678901,\n" +
                                                            "  \"password\": \"A.v_1234\"\n" +
                                                            "}"
                                            )
                                    }
                            ),
                    }
            )
    )
    @PostMapping
    public ResponseEntity save(@RequestBody CusCustomerSaveRequestDto cusCustomerSaveRequestDto){

        CusCustomerDto cusCustomerDto = cusCustomerService.save(cusCustomerSaveRequestDto);

        WebMvcLinkBuilder linkGet = WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(
                        this.getClass()).findById(cusCustomerDto.getId()));

        WebMvcLinkBuilder linkDelete = WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(
                        this.getClass()).delete(cusCustomerDto.getId()));

        EntityModel entityModel = EntityModel.of(cusCustomerDto);

        entityModel.add(linkGet.withRel("find-by-id"));
        entityModel.add(linkDelete.withRel("delete"));

        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(entityModel);

        return ResponseEntity.ok(RestResponse.of(mappingJacksonValue));
    }

    @Operation(tags = "Customer Controller")
    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id){

        cusCustomerService.delete(id);

        return ResponseEntity.ok(RestResponse.empty());
    }

    @Operation(tags = "Customer Controller")
    @PutMapping
    public ResponseEntity update(@RequestBody CusCustomerUpdateRequestDto cusCustomerUpdateRequestDto){

        CusCustomerDto cusCustomerDto = cusCustomerService.update(cusCustomerUpdateRequestDto);

        return ResponseEntity.ok(RestResponse.of(cusCustomerDto));
    }
}
