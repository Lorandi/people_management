//package com.lorandi.peoplemanagement.resource;
//
//
//import com.lorandi.peoplemanagement.dto.AddressDTO;
//import com.lorandi.peoplemanagement.service.AddressService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/addresses")
//@RequiredArgsConstructor
////@Tag(name = "Addresses")
//public class AddressResource {
//
//    private final AddressService service;
//
//
//    @GetMapping("/{id}")
////    @Operation(summary = "Search Address by id",
////            security = {@SecurityRequirement(name = "bearerAuth")},
////            responses = {@ApiResponse(responseCode = "200", description = "Resource successfully retrieved",
////                    content = @Content(schema = @Schema(implementation = AddressDTO.class)))})
//    public AddressDTO findById(@PathVariable Long id) {
//        return service.findDTOById(id);
//    }
//}
//
////    @PostMapping
////    @ResponseStatus(CREATED)
////    @Operation(summary = "Create address",
////            security = {@SecurityRequirement(name = "bearerAuth")},
////            responses = {@ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = AddressRequestDTO.class)))})
////    public AddressDTO create(@RequestBody AddressRequestDTO addressRequest) {
////        return service.create(addressRequest);
////    }
////
////    @PutMapping
////    @Operation(summary = "Update address by id",
////            security = {@SecurityRequirement(name = "bearerAuth")},
////            responses = {@ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = AddressDTO.class)))})
////    public AddressDTO update(@RequestBody AddressUpdateDTO addressRequest) {
////        return service.update(addressRequest);
////    }
////
////    @GetMapping
////    @Operation(summary = "Find all addresses",
////            security = {@SecurityRequirement(name = "bearerAuth")},//
////            responses = {@ApiResponse(responseCode = "200",
////                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = AddressDTO.class))))})
////    public Page<AddressDTO> findAll(@RequestParam(required = false) Optional<List<Long>> personId,
////                                       @RequestParam(required = false) Optional<String> street,
////                                       @RequestParam(required = false) Optional<String> zipcode,
////                                       @RequestParam(required = false) Optional<String> number,
////                                       @RequestParam(required = false) Optional<String> city,
////                                       @RequestParam(required = false) Optional<List<AddressTypeEnum>> addressType,
////                                       @RequestParam(defaultValue = "0") Integer page,
////                                       @RequestParam(defaultValue = "10") Integer size,
////                                       @RequestParam(defaultValue = "id") String sort,
////                                       @RequestParam(defaultValue = "DESC") Sort.Direction direction) {
////        return service.findAll(personId, street, zipcode, number, city, addressType,
////                PageRequest.of(page, size, Sort.by(direction, sort)));
////    }
////}
