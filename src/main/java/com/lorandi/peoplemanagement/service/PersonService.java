package com.lorandi.peoplemanagement.service;

import com.lorandi.peoplemanagement.dto.PersonDTO;
import com.lorandi.peoplemanagement.dto.PersonRequestDTO;
import com.lorandi.peoplemanagement.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.lorandi.peoplemanagement.util.mapper.MapperConstants.personMapper;

@Slf4j
@Service
@RequiredArgsConstructor
public class PersonService {
    private final PersonRepository repository;
    private final AddressService addressService;

    public PersonDTO create(final PersonRequestDTO requestDTO){
        var person = personMapper.buildPersonDTO(repository.save(personMapper.buildPerson(requestDTO)));





        return person;
    }
}
