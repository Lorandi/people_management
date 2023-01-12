package com.lorandi.peoplemanagement.repository.spec;

import com.lorandi.peoplemanagement.entity.Person;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

@Getter
@Builder
public class PersonSpecification implements Specification<Person> {

    @Builder.Default
    private final transient Optional<String> name = Optional.empty();
    @Builder.Default
    private final transient Optional<LocalDate> birthdate = Optional.empty();


    @Override
    public Predicate toPredicate(Root<Person> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder builder) {
        final var predicates = new ArrayList<Predicate>();
        name.ifPresent(s -> predicates.add(builder.like(builder
                .lower(root.get("name")), "%" + s.toLowerCase() + "%")));
        birthdate.ifPresent(s -> predicates.add(root.get("birthdate").in(s)));
        criteriaQuery.distinct(true);
        return builder.and(predicates.toArray(new Predicate[0]));
    }
}
