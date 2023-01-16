package com.lorandi.peoplemanagement.dto;

import com.lorandi.peoplemanagement.enums.AddressTypeEnum;
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

    String street;
    String zipcode;
    String number;
    String city;
    AddressTypeEnum addressType;
    Boolean mainAddress;
}
