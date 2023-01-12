package com.lorandi.peoplemanagement.service;

import com.lorandi.peoplemanagement.dto.AddressDTO;
import com.lorandi.peoplemanagement.dto.AddressRequestDTO;
import com.lorandi.peoplemanagement.entity.Address;
import com.lorandi.peoplemanagement.helper.MessageHelper;
import com.lorandi.peoplemanagement.repository.AddressRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.lorandi.peoplemanagement.util.mapper.MapperConstants.addressMapper;

@Slf4j
@Service
@RequiredArgsConstructor
public class AddressService {

    private final AddressRepository repository;

    public AddressDTO create(final AddressRequestDTO requestDTO){
        var address = addressMapper.buildAddressDTO(repository.save(addressMapper.buildAddress(requestDTO)));

        return address;
    }

    public List<Address> findAllByPersonId(final Long personId){
        return repository.findAllByPersonId(personId);
    }

    public void save(final Address address){
        repository.save(address);
    }

    public void deleteAllByPersonId(final Long personId){
        repository.deleteAllByPersonId(personId);
    }
}
