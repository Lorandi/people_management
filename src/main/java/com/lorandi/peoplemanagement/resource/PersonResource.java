package com.lorandi.peoplemanagement.resource;


import com.lorandi.peoplemanagement.dto.*;
import com.lorandi.peoplemanagement.enums.AddressTypeEnum;
import com.lorandi.peoplemanagement.service.PersonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/person")
@RequiredArgsConstructor
@Tag(name = "person")
public class PersonResource {

    private final PersonService service;

    @GetMapping("/{id}")
    @Operation(summary = "Search person by id",
            responses = {@ApiResponse(responseCode = "200", description = "Resource successfully retrieved",
                    content = @Content(schema = @Schema(implementation = PersonDTO.class)))})
    public PersonDTO findById(@PathVariable Long id) {
        return service.findDTObyId(id);
    }

    @PostMapping
    @ResponseStatus(CREATED)
    @Operation(summary = "Create person",
            responses = {@ApiResponse(responseCode = "201", content = @Content(schema = @Schema(implementation = PersonDTO.class)))})
    public PersonDTO create(@RequestBody PersonRequestDTO requestDTO) {
        return service.create(requestDTO);
    }

    @PutMapping
    @Operation(summary = "Update person by id",
            responses = {@ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = PersonDTO.class)))})
    public PersonDTO update(@RequestBody PersonUpdateDTO updateDTO) {
        return service.update(updateDTO);
    }

    @GetMapping
    @Operation(summary = "Find all people",
            responses = {@ApiResponse(responseCode = "200",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = AddressDTO.class))))})
    public Page<PersonDTO> findAll( @RequestParam(required = false) Optional<String> name,
                                    @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Optional<LocalDate> birthdate,
                                    @RequestParam(defaultValue = "0") Integer page,
                                    @RequestParam(defaultValue = "10") Integer size,
                                    @RequestParam(defaultValue = "id") String sort,
                                    @RequestParam(defaultValue = "DESC") Sort.Direction direction) {
        return service.findAll(name, birthdate, PageRequest.of(page, size, Sort.by(direction, sort)));
    }
}
