package com.lorandi.peoplemanagement.util.mapper;

import com.lorandi.peoplemanagement.dto.AddressDTO;
import com.lorandi.peoplemanagement.dto.AddressRequestDTO;
import com.lorandi.peoplemanagement.entity.Address;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface AddressMapper {

    Address buildAddress(AddressRequestDTO addressDTO);

    AddressDTO buildAddressDTO(Address address);
}