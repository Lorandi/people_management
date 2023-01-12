package com.lorandi.peoplemanagement.enums;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.lorandi.peoplemanagement.enums.serializer.EnumSerializer;
import lombok.AllArgsConstructor;
import lombok.Getter;

@JsonSerialize(using = EnumSerializer.class)
@AllArgsConstructor
@Getter
public enum AddressTypeEnum implements EnumDescription{

    HOME("Casa"),
    WORK("Trabalho");

    private final String description;

}
