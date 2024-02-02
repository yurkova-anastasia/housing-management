package ru.clevertec.house.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.clevertec.house.model.Person;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PersonRepository extends JpaRepository<Person, Long> {

    @Query(value = """
            select
            	p.*
            from
            	people p
            join house_history hh on
            	p.id = hh.person_id
            join houses h on
            	hh.house_id = h.id
            where
            	h.uuid = :houseId
            	and hh.type = 'TENANT'
            limit :#{#pageable.getPageSize()} offset :#{#pageable.getOffset()}
            """, nativeQuery = true)
    Page<Person> findAllPreviousResidentsOfHouse(UUID houseId, Pageable pageable);

    @Query(value = """
            select
            	p.*
            from
            	people p
            join house_history hh on
            	p.id = hh.person_id
            join houses h on
            	hh.house_id = h.id
            where
            	h.uuid = :houseId
            	and hh.type = 'OWNER'
            limit :#{#pageable.getPageSize()} offset :#{#pageable.getOffset()}
            """, nativeQuery = true)
    Page<Person> findAllPreviousOwnersOfHouse(UUID houseId, Pageable pageable);

    Optional<Person> findByUuid(UUID id);

    Page<Person> findPersonsByResidency_Uuid(UUID uuid, Pageable pageable);

    void deleteByUuid(UUID id);

}
