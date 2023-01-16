package com.lorandi.peoplemanagement.util.creator;

import com.lorandi.peoplemanagement.dto.PersonDTO;
import com.lorandi.peoplemanagement.dto.PersonRequestDTO;
import com.lorandi.peoplemanagement.dto.PersonUpdateDTO;
import com.lorandi.peoplemanagement.entity.Person;

import java.util.List;

import static com.lorandi.peoplemanagement.util.PodamFactory.podam;
import static com.lorandi.peoplemanagement.util.mapper.MapperConstants.personMapper;


public class PersonCreator {

    public static final Person person = podam.manufacturePojo(Person.class);
    public static final PersonDTO personDTO = personMapper.buildPersonDTO(person).withAddress(List.of());

    public static PersonRequestDTO createPersonRequestDTO() {
        return PersonRequestDTO.builder()
                .name(person.getName())
                .birthdate(person.getBirthdate())
                .address(List.of())
                .build();
    }

    public static PersonUpdateDTO createPersonUpdateDTO() {
        return PersonUpdateDTO.builder()
                .id(person.getId())
                .name(person.getName())
                .birthdate(person.getBirthdate())
                .address(List.of())
                .build();
    }
}
