package com.lorandi.peoplemanagement.entity;

import com.lorandi.peoplemanagement.enums.AddressTypeEnum;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Data
@Builder
@With
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "address")
public class Address {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private Long personId;
    private String street;
    private String zipcode;
    private String number;
    private String city;
    private AddressTypeEnum addressType;
    private Boolean mainAddress;
}
