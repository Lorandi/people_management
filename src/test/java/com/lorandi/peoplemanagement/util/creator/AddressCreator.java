package com.lorandi.peoplemanagement.util.creator;

import com.lorandi.peoplemanagement.dto.AddressDTO;
import com.lorandi.peoplemanagement.dto.AddressRequestDTO;
import com.lorandi.peoplemanagement.dto.AddressUpdateDTO;
import com.lorandi.peoplemanagement.entity.Address;

import static com.lorandi.peoplemanagement.util.PodamFactory.podam;
import static com.lorandi.peoplemanagement.util.mapper.MapperConstants.addressMapper;


public class AddressCreator {

    public static final Address address = podam.manufacturePojo(Address.class);
    public static final AddressDTO addressDTO = addressMapper.buildAddressDTO(address);

    public static AddressRequestDTO createAddressRequestDTO() {
        return AddressRequestDTO.builder()
                .street(address.getStreet())
                .zipcode(address.getZipcode())
                .number(address.getNumber())
                .city(address.getCity())
                .addressType(address.getAddressType())
                .mainAddress(address.getMainAddress())
                .build();
    }

    public static AddressUpdateDTO addressUpdateDTO() {
        return AddressUpdateDTO.builder()
                .id(address.getId())
                .street(address.getStreet())
                .zipcode(address.getZipcode())
                .number(address.getNumber())
                .city(address.getCity())
                .addressType(address.getAddressType())
                .mainAddress(address.getMainAddress())
                .build();
    }
}
