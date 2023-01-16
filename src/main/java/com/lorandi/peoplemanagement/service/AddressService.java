package com.lorandi.peoplemanagement.service;

import com.lorandi.peoplemanagement.dto.AddressDTO;
import com.lorandi.peoplemanagement.dto.AddressRequestDTO;
import com.lorandi.peoplemanagement.dto.AddressUpdateDTO;
import com.lorandi.peoplemanagement.entity.Address;
import com.lorandi.peoplemanagement.enums.AddressTypeEnum;
import com.lorandi.peoplemanagement.helper.MessageHelper;
import com.lorandi.peoplemanagement.repository.AddressRepository;
import com.lorandi.peoplemanagement.repository.spec.AddressSpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static com.lorandi.peoplemanagement.exception.ErrorCodeEnum.ERROR_ADDRESS_NOT_FOUND;
import static com.lorandi.peoplemanagement.util.mapper.MapperConstants.addressMapper;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Slf4j
@Service
@RequiredArgsConstructor
public class AddressService {

    private final AddressRepository repository;
    private final MessageHelper messageHelper;

    public AddressDTO create(final AddressRequestDTO requestDTO, Long personId) {
        var address = addressMapper.buildAddressDTO(repository.save(addressMapper.buildAddress(requestDTO)
                .withPersonId(personId)));

        setOtherMainAddressToFalse(address);

        return address;
    }

    public AddressDTO update(final AddressUpdateDTO updateDTO) {
        var address = this.findById(updateDTO.getId());

        var updatedAddress = addressMapper.buildAddressDTO(repository.save(address
                .withStreet(updateDTO.getStreet())
                .withZipcode(updateDTO.getZipcode())
                .withNumber(updateDTO.getNumber())
                .withCity(updateDTO.getCity())
                .withAddressType(updateDTO.getAddressType())
                .withMainAddress(updateDTO.getMainAddress())));

        setOtherMainAddressToFalse(updatedAddress);

        return updatedAddress;
    }

    public Address findById(final Long id) {
        return repository.findById(id).orElseThrow(() -> {
            log.error(messageHelper.get(ERROR_ADDRESS_NOT_FOUND, id.toString()));
            return new ResponseStatusException(NOT_FOUND, messageHelper.get(ERROR_ADDRESS_NOT_FOUND, id.toString()));
        });
    }

    public AddressDTO findDTOById(Long id) {
        Address address = findById(id);
        return addressMapper.buildAddressDTO(address);
    }

    public Page<AddressDTO> findAll(final Optional<List<Long>> personId,
                                    final Optional<String> street,
                                    final Optional<String> zipcode,
                                    final Optional<String> number,
                                    final Optional<String> city,
                                    final Optional<List<AddressTypeEnum>> addressType,
                                    final Pageable pageable) {
        return repository.findAll(AddressSpecification.builder()
                        .personId(personId)
                        .street(street)
                        .zipcode(zipcode)
                        .number(number)
                        .city(city)
                        .addressType(addressType)
                        .build(), pageable)
                .map(addressMapper::buildAddressDTO);
    }

    public void delete(final Long id) {
        Address address = findById(id);
        ;
        repository.delete(address);
    }


    public List<Address> findAllByPersonId(final Long personId) {
        return repository.findAllByPersonId(personId);
    }

    public void deleteAllByPersonId(final Long personId) {
        repository.deleteAllByPersonId(personId);
    }

    public void setOtherMainAddressToFalse(final AddressDTO address) {
        if (address.getMainAddress()) {
            var allAddress = this.findAllByPersonId(address.getPersonId());
            allAddress.forEach(one -> {
                if (!one.getId().equals(address.getId()) && one.getMainAddress()) {
                    repository.save(one.withMainAddress(false));
                }
            });
        }
    }
}
