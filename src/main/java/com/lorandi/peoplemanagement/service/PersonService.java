package com.lorandi.peoplemanagement.service;

import com.lorandi.peoplemanagement.dto.*;
import com.lorandi.peoplemanagement.entity.Person;
import com.lorandi.peoplemanagement.helper.MessageHelper;
import com.lorandi.peoplemanagement.repository.PersonRepository;
import com.lorandi.peoplemanagement.repository.spec.PersonSpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

import static com.lorandi.peoplemanagement.exception.ErrorCodeEnum.ERROR_PERSON_NOT_FOUND;
import static com.lorandi.peoplemanagement.util.mapper.MapperConstants.personMapper;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Slf4j
@Service
@RequiredArgsConstructor
public class PersonService {
    private final PersonRepository repository;
    private final AddressService addressService;
    private final MessageHelper messageHelper;

    public PersonDTO create(final PersonRequestDTO request) {
        var person = personMapper.buildPersonDTO(repository.save(personMapper.buildPerson(request)));

        ArrayList<AddressDTO> addresses = new ArrayList<>();

        request.getAddress().forEach(address -> {
            addresses.add(addressService.create(AddressRequestDTO.builder()
                    .street(address.getStreet())
                    .zipcode(address.getZipcode())
                    .number(address.getNumber())
                    .city(address.getCity())
                    .addressType(address.getAddressType())
                    .mainAddress(address.getMainAddress())
                    .build(), person.getId()));

        });
        return this.findDTObyId(person.getId());
    }

    @Transactional
    public PersonDTO update(final PersonUpdateDTO updateDTO) {
        var person = this.findById(updateDTO.getId());
        personMapper.buildPersonDTO(repository.save(person.withName(updateDTO.getName())
                .withBirthdate(updateDTO.getBirthdate())));

        addressService.deleteAllByPersonId(updateDTO.getId());

        ArrayList<AddressDTO> addresses = new ArrayList<>();

        updateDTO.getAddress().forEach(address -> {
            addresses.add(addressService.create(AddressRequestDTO.builder()
                    .street(address.getStreet())
                    .zipcode(address.getZipcode())
                    .number(address.getNumber())
                    .city(address.getCity())
                    .addressType(address.getAddressType())
                    .mainAddress(address.getMainAddress())
                    .build(), person.getId()));
        });

        return this.findDTObyId(person.getId());
    }

    public Person findById(final Long id) {
        return repository.findById(id).orElseThrow(() -> {
            log.error(messageHelper.get(ERROR_PERSON_NOT_FOUND, id.toString()));
            return new ResponseStatusException(NOT_FOUND, messageHelper.get(ERROR_PERSON_NOT_FOUND, id.toString()));
        });
    }

    public PersonDTO findDTObyId(final Long id) {
        var person = findById(id);
        var addresses = addressService.findAllByPersonId(id);
        ArrayList<AddressDTO> addressesDTO = new ArrayList<>();
        addresses.forEach(address -> {
            addressesDTO.add(AddressDTO.builder()
                    .id(address.getId())
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
                    addresses.forEach(address -> {
                        addressesDTO.add(AddressDTO.builder()
                                .id(address.getId())
                                .personId(address.getPersonId())
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
