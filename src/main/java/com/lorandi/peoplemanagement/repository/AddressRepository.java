package com.lorandi.peoplemanagement.repository;

import com.lorandi.peoplemanagement.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long>, JpaSpecificationExecutor<Address> {

    List<Address> findAllByPersonId(Long id);

    void deleteAllByPersonId(Long id);
}
