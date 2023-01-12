package com.lorandi.peoplemanagement.dto;

import lombok.Builder;
import lombok.Value;
import lombok.With;
import lombok.extern.jackson.Jacksonized;

import javax.validation.constraints.NotNull;

@Value
@With
@Jacksonized
@Builder
public class AddressRequestDTO {

    @NotNull
    Long personId;
    String street;
    String zipcode;
    String number;
    String city;
    Boolean mainAddress;
}
