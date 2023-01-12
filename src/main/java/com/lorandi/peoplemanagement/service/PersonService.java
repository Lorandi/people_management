package com.lorandi.peoplemanagement.service;

import com.lorandi.peoplemanagement.dto.*;
import com.lorandi.peoplemanagement.entity.Person;
import com.lorandi.peoplemanagement.repository.PersonRepository;
import com.lorandi.peoplemanagement.repository.spec.PersonSpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

import static com.lorandi.peoplemanagement.util.mapper.MapperConstants.personMapper;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Slf4j
@Service
@RequiredArgsConstructor
public class PersonService {
    private final PersonRepository repository;
    private final AddressService addressService;
//    private final MessageHelper messageHelper;

    public PersonDTO create(final PersonRequestDTO requestDTO) {
        var person = personMapper.buildPersonDTO(repository.save(personMapper.buildPerson(requestDTO)));

        ArrayList<AddressDTO> addresses = new ArrayList<>();

        requestDTO.getAddress().forEach(address -> {
            if (address.getMainAddress()) {
                var allAddress = addressService.findAllByPersonId(address.getPersonId());
                allAddress.forEach(one -> {
                    if (one.getMainAddress()) {
                        addressService.save(one.withMainAddress(false));
                    }
                });
            }

            addresses.add(addressService.create(AddressRequestDTO.builder()
                    .personId(person.getId())
                    .street(address.getStreet())
                    .zipcode(address.getZipcode())
                    .number(address.getNumber())
                    .city(address.getCity())
                    .addressType(address.getAddressType())
                    .mainAddress(address.getMainAddress())
                    .build()));

        });
//        return person.withAddress(addresses);

        return PersonDTO.builder()
                .id(person.getId())
                .name(person.getName())
                .birthdate(person.getBirthdate())
                .address(addresses)
                .build();
    }

    public PersonDTO update(final PersonUpdateDTO updateDTO) {
        var person = this.findById(updateDTO.getId());
        var updatedPerson = personMapper.buildPersonDTO(repository.save(person
                .withName(updateDTO.getName())
                .withBirthdate(updateDTO.getBirthdate())));

        addressService.deleteAllByPersonId(updateDTO.getId());

        ArrayList<AddressDTO> addresses = new ArrayList<>();

        updateDTO.getAddress().forEach(address -> {
            if (address.getMainAddress()) {
                var allAddress = addressService.findAllByPersonId(address.getPersonId());
                allAddress.forEach(one -> {
                    if (one.getMainAddress()) {
                        addressService.save(one.withMainAddress(false));
                    }
                });
            }

            addresses.add(addressService.create(AddressRequestDTO.builder()
                    .personId(person.getId())
                    .street(address.getStreet())
                    .zipcode(address.getZipcode())
                    .number(address.getNumber())
                    .city(address.getCity())
                    .addressType(address.getAddressType())
                    .mainAddress(address.getMainAddress())
                    .build()));

        });

//        return updatedPerson.withAddress(addresses);
        return PersonDTO.builder()
                .id(updatedPerson.getId())
                .name(updatedPerson.getName())
                .birthdate(updatedPerson.getBirthdate())
                .address(addresses)
                .build();
    }


    public Person findById(final Long id) {
        return repository.findById(id).orElseThrow(() -> {
//            log.error(messageHelper.get(ERROR_PERSON_NOT_FOUND, id.toString()));
//            return new ResponseStatusException(NOT_FOUND, messageHelper.get(ERROR_PERSON_NOT_FOUND, id.toString()));
            return new ResponseStatusException(NOT_FOUND);
        });
    }

    public PersonDTO findDTObyId(final Long id) {
        var person = findById(id);
        var addresses = addressService.findAllByPersonId(id);
        ArrayList<AddressDTO> addressesDTO = new ArrayList<>();
        addresses.forEach(address ->{
            addressesDTO.add(AddressDTO.builder()
                    .personId(person.getId())
                    .street(address.getStreet())
                    .zipcode(address.getZipcode())
                    .number(address.getNumber())
                    .city(address.getCity())
                    .addressType(address.getAddressType())
                    .mainAddress(address.getMainAddress())
                    .build());
        });
        return personMapper.buildPersonDTO(person).withAddress(addressesDTO);
    }

    public Page<PersonDTO> findAll(final Optional<String> name,
                                   final Optional<LocalDate> birthdate,
                                   final Pageable pageable) {
        return repository.findAll(PersonSpecification.builder()
                        .name(name)
                        .birthdate(birthdate)
                        .build(), pageable)
                .map(person -> {
                    var addresses = addressService.findAllByPersonId(person.getId());
                    ArrayList<AddressDTO> addressesDTO = new ArrayList<>();
                    addresses.forEach(address ->{
                        addressesDTO.add(AddressDTO.builder()
                                .personId(person.getId())
                                .street(address.getStreet())
                                .zipcode(address.getZipcode())
                                .number(address.getNumber())
                                .city(address.getCity())
                                .addressType(address.getAddressType())
                                .mainAddress(address.getMainAddress())
                                .build());
                    });
                    return personMapper.buildPersonDTO(person).withAddress(addressesDTO);
                });
    }

    public void delete(final Long id) {
        Person person = this.findById(id);
        addressService.deleteAllByPersonId(id);
        repository.delete(person);
    }
}
