package com.lorandi.peoplemanagement.dto;

import com.lorandi.peoplemanagement.enums.AddressTypeEnum;
import lombok.Builder;
import lombok.Value;
import lombok.With;
import lombok.extern.jackson.Jacksonized;

@Value
@With
@Jacksonized
@Builder
public class AddressDTO {
    Long id;
    Long personId;
    String street;
    String zipcode;
    String number;
    String city;
    AddressTypeEnum addressType;
    Boolean mainAddress;
}
