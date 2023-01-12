package com.lorandi.peoplemanagement.repository.spec;

import com.lorandi.peoplemanagement.entity.Address;
import com.lorandi.peoplemanagement.enums.AddressTypeEnum;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
@Builder
public class AddressSpecification implements Specification<Address> {
    @Setter
    @Builder.Default
    private Optional<List<Long>> personId = Optional.empty();
    @Builder.Default
    private final transient Optional<String> street = Optional.empty();
    @Builder.Default
    private final transient Optional<String> zipcode = Optional.empty();
    @Builder.Default
    private final transient Optional<String> number = Optional.empty();
    @Builder.Default
    private final transient Optional<String> city = Optional.empty();
    @Builder.Default
    private final transient Optional<List<AddressTypeEnum>> addressType = Optional.empty();

    @Override
    public Predicate toPredicate(Root<Address> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder builder) {
        final var predicates = new ArrayList<Predicate>();
        personId.ifPresent(s -> predicates.add(root.get("personId").in(s)));
        street.ifPresent(s -> predicates.add(builder.like(builder
                .lower(root.get("street")), "%" + s.toLowerCase() + "%")));
        zipcode.ifPresent(s -> predicates.add(builder.like(builder
                .lower(root.get("zipcode")), "%" + s.toLowerCase() + "%")));
        number.ifPresent(s -> predicates.add(builder.like(builder
                .lower(root.get("number")), "%" + s.toLowerCase() + "%")));
        city.ifPresent(s -> predicates.add(builder.like(builder
                .lower(root.get("city")), "%" + s.toLowerCase() + "%")));
        addressType.ifPresent(s -> predicates.add(root.get("addressType").in(s)));

        criteriaQuery.distinct(true);
        return builder.and(predicates.toArray(new Predicate[0]));
    }
}
