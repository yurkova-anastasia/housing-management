package ru.clevertec.house.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.clevertec.house.model.House;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface HouseRepository extends JpaRepository<House, Long> {

    @Query(value = """
            SELECT 
                h.*
            from
            	houses h
            join house_history hh on
            	h.id = hh.house_id
            join people p on
            	hh.person_id = p.id
            where
            	p.uuid = :personId
            	and hh.type = 'TENANT'
            limit :#{#pageable.getPageSize()} offset :#{#pageable.getOffset()}
            """, nativeQuery = true)
    Page<House> findAllPreviousResidencyOfPerson(UUID personId, Pageable pageable);

    @Query(value = """
            SELECT  
                h.*
            from
            	houses h
            join house_history hh on
            	h.id = hh.house_id
            join people p on
            	hh.person_id = p.id
            where
            	p.uuid = :personId
            	and hh.type = 'OWNER'
            limit :#{#pageable.getPageSize()} offset :#{#pageable.getOffset()}
            """, nativeQuery = true)
    Page<House> findAllPreviousOwnedHousesOfPerson(UUID personId, Pageable pageable);

    Optional<House> findByUuid(UUID uuid);

    Page<House> findHousesByOwners_Uuid(UUID uuid, Pageable pageable);

    void deleteByUuid(UUID uuid);

}
