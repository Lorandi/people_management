package com.lorandi.peoplemanagement.util.mapper;

import org.mapstruct.factory.Mappers;

public class MapperConstants {

    private MapperConstants() {

    }
    public static final AddressMapper addressMapper = Mappers.getMapper(AddressMapper.class);
    public static final PersonMapper personMapper = Mappers.getMapper(PersonMapper.class);

}