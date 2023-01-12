package com.lorandi.peoplemanagement.service;

import com.lorandi.peoplemanagement.dto.AddressDTO;
import com.lorandi.peoplemanagement.dto.AddressRequestDTO;
import com.lorandi.peoplemanagement.dto.PersonDTO;
import com.lorandi.peoplemanagement.dto.PersonRequestDTO;
import com.lorandi.peoplemanagement.repository.AddressRepository;
import com.lorandi.peoplemanagement.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.lorandi.peoplemanagement.util.mapper.MapperConstants.addressMapper;
import static com.lorandi.peoplemanagement.util.mapper.MapperConstants.personMapper;

@Slf4j
@Service
@RequiredArgsConstructor
public class AddressService {
    private final AddressRepository repository;

    public AddressDTO create(final AddressRequestDTO requestDTO){
        var address = addressMapper.buildAddressDTO(repository.save(addressMapper.buildAddress(requestDTO)));

        return address;
    }
}
