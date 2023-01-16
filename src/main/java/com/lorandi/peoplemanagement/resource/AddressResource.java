package com.lorandi.peoplemanagement.resource;


import com.lorandi.peoplemanagement.dto.AddressDTO;
import com.lorandi.peoplemanagement.dto.AddressRequestDTO;
import com.lorandi.peoplemanagement.dto.AddressUpdateDTO;
import com.lorandi.peoplemanagement.enums.AddressTypeEnum;
import com.lorandi.peoplemanagement.service.AddressService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/address")
@RequiredArgsConstructor
@Tag(name = "Address")
public class AddressResource {

    private final AddressService service;

    @GetMapping("/{id}")
    @Operation(summary = "Search address by id",
            responses = {@ApiResponse(responseCode = "200", description = "Resource successfully retrieved",
                    content = @Content(schema = @Schema(implementation = AddressDTO.class)))})
    public AddressDTO findById(@PathVariable Long id) {
        return service.findDTOById(id);
    }

//    @PostMapping
//    @ResponseStatus(CREATED)
//    @Operation(summary = "Create address",
//            responses = {@ApiResponse(responseCode = "201", content = @Content(schema = @Schema(implementation = AddressRequestDTO.class)))})
//    public AddressDTO create(@RequestBody AddressRequestDTO addressRequest, @RequestBody Long personId) {
//        return service.create(addressRequest, personId);
//    }

    @PutMapping
    @Operation(summary = "Update address by id",
            responses = {@ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = AddressDTO.class)))})
    public AddressDTO update(@RequestBody AddressUpdateDTO addressRequest) {
        return service.update(addressRequest);
    }

    @GetMapping
    @Operation(summary = "Find all addresses",
            responses = {@ApiResponse(responseCode = "200",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = AddressDTO.class))))})
    public Page<AddressDTO> findAll(@RequestParam(required = false) Optional<List<Long>> personId,
                                    @RequestParam(required = false) Optional<String> street,
                                    @RequestParam(required = false) Optional<String> zipcode,
                                    @RequestParam(required = false) Optional<String> number,
                                    @RequestParam(required = false) Optional<String> city,
                                    @RequestParam(required = false) Optional<List<AddressTypeEnum>> addressType,
                                    @RequestParam(defaultValue = "0") Integer page,
                                    @RequestParam(defaultValue = "10") Integer size,
                                    @RequestParam(defaultValue = "id") String sort,
                                    @RequestParam(defaultValue = "DESC") Sort.Direction direction) {
        return service.findAll(personId, street, zipcode, number, city, addressType,
                PageRequest.of(page, size, Sort.by(direction, sort)));
    }
}
