package com.lorandi.peoplemanagement.service;

import com.lorandi.peoplemanagement.helper.MessageHelper;
import com.lorandi.peoplemanagement.repository.AddressRepository;
import com.lorandi.peoplemanagement.repository.PersonRepository;
import com.lorandi.peoplemanagement.repository.spec.PersonSpecification;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static com.lorandi.peoplemanagement.util.creator.AddressCreator.address;
import static com.lorandi.peoplemanagement.util.creator.AddressCreator.addressDTO;
import static com.lorandi.peoplemanagement.util.creator.PersonCreator.*;
import static java.util.Optional.empty;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.data.domain.Sort.Direction.ASC;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@ExtendWith(SpringExtension.class)
public class PersonServiceTest {
    @InjectMocks
    private PersonService service;
    @Mock
    private PersonRepository repository;
    @Mock
    private AddressService addressService;
    @Mock
    private AddressRepository addressRepository;
    @Mock
    private MessageHelper messageHelper;

    @Test
    void create_returnsPersonDTO_WhenSuccessful() {
        when(repository.save(any())).thenReturn(person);
        when(repository.findById(any())).thenReturn(Optional.of(person));
        assertEquals(personDTO, service.create(createPersonRequestDTO()));
    }

    @Test
    void update_returnsPersonDTO_WhenSuccessful() {
        when(repository.save(any())).thenReturn(person);
        when(repository.findById(any())).thenReturn(Optional.of(person));
        assertEquals(personDTO, service.update(createPersonUpdateDTO()));
    }

    @Test
    void findById_throws404_whenAddressNotFound() {
        when(repository.findById(person.getId())).thenReturn(Optional.empty());
        final var exception = assertThrows(ResponseStatusException.class, () ->
                service.findById(person.getId())).getStatus();
        assertEquals(NOT_FOUND, exception);
    }

    @Test
    void findDtoById_whenSuccessful() {
        when(repository.findById(person.getId())).thenReturn(Optional.of(person));
        assertEquals(personDTO, service.findDTObyId(person.getId()));
    }

    @Test
    void delete_removesEntity_whenSuccessful() {
        when(repository.findById(person.getId())).thenReturn(Optional.of(person));
        service.delete(person.getId());
        verify(repository, times(1)).delete(person);
    }

    @Test
    void findAll_returnsPageOfDTOs_whenSuccessful() {
        final var pageable = PageRequest.of(0, 10, Sort.by(ASC, "id"));
        final var assertion = new PageImpl<>(List.of(personDTO));
        final var people = new PageImpl<>(List.of(person));
        when(repository.findAll(any(PersonSpecification.class), any(Pageable.class))).thenReturn(people);
        assertEquals(assertion, service.findAll(empty(), empty(), pageable));
    }
}
