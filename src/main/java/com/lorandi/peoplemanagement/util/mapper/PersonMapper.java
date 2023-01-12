package com.lorandi.peoplemanagement.util.mapper;

import com.lorandi.peoplemanagement.dto.PersonDTO;
import com.lorandi.peoplemanagement.dto.PersonRequestDTO;
import com.lorandi.peoplemanagement.entity.Person;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface PersonMapper {

    @Mapping(target = "id", source = "true")
    Person buildPerson(PersonRequestDTO personDTO);

    PersonDTO buildPersonDTO(Person person);
}