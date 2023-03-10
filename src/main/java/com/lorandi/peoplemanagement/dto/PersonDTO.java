package com.lorandi.peoplemanagement.dto;

import lombok.Builder;
import lombok.Value;
import lombok.With;
import lombok.extern.jackson.Jacksonized;

import java.time.LocalDate;
import java.util.List;

@Value
@With
@Jacksonized
@Builder
public class PersonDTO {
    Long id;
    String name;
    LocalDate birthdate;
    List<AddressDTO> address;
}
