package com.lorandi.peoplemanagement.service;

import com.lorandi.peoplemanagement.helper.MessageHelper;
import com.lorandi.peoplemanagement.repository.AddressRepository;
import com.lorandi.peoplemanagement.repository.spec.AddressSpecification;
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

import static com.lorandi.peoplemanagement.util.creator.AddressCreator.*;
import static java.util.Optional.empty;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;
import static org.springframework.data.domain.Sort.Direction.ASC;
import static org.springframework.http.HttpStatus.*;

@ExtendWith(SpringExtension.class)
public class AddressServiceTest {
    @InjectMocks
    private AddressService service;
    @Mock
    private AddressRepository repository;
    @Mock
    private MessageHelper messageHelper;

    @Test
    void create_returnsAddressDTO_WhenSuccessful() {
        when(repository.save(any())).thenReturn(address);
        assertEquals(addressDTO, service.create(createAddressRequestDTO(), address.getPersonId()));
    }

    @Test
    void update_returnsAddressDTO_WhenSuccessful() {
        when(repository.findById(any())).thenReturn(Optional.of(address));
        when(repository.save(any())).thenReturn(address);
        assertEquals(addressDTO, service.update(addressUpdateDTO()));
    }
    @Test
    void findById_throws404_whenAddressNotFound() {
        when(repository.findById(address.getId())).thenReturn(Optional.empty());
        final var exception = assertThrows(ResponseStatusException.class, () ->
                service.findById(address.getId())).getStatus();
        assertEquals(NOT_FOUND, exception);
    }

    @Test
    void findDtoById_whenSuccessful() {
        when(repository.findById(address.getId())).thenReturn(Optional.of(address));
        assertEquals(addressDTO, service.findDTOById(address.getId()));
    }

    @Test
    void delete_removesEntity_whenSuccessful() {
        when(repository.findById(address.getId())).thenReturn(Optional.of(address));
        service.delete(address.getId());
        verify(repository, times(1)).delete(address);
    }

    @Test
    void deleteAllByPersonId_removesEntity_whenSuccessful() {
        when(repository.findById(address.getId())).thenReturn(Optional.of(address));
        service.deleteAllByPersonId(address.getId());
        verify(repository, times(1)).deleteAllByPersonId(address.getId());
    }

    @Test
    void findAll_returnsPageOfDTOs_whenSuccessful() {
        final var pageable = PageRequest.of(0, 10, Sort.by(ASC, "id"));
        final var assertion = new PageImpl<>(List.of(addressDTO));
        final var addresses = new PageImpl<>(List.of(address));
        when(repository.findAll(any(AddressSpecification.class), any(Pageable.class))).thenReturn(addresses);
        assertEquals(assertion, service.findAll(empty(), empty(), empty(), empty(), empty(),empty(),pageable));
    }

}


