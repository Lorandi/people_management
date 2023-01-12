package com.lorandi.peoplemanagement.entity;


import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@Builder
@With
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "person")
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private LocalDate birthdate;
}
