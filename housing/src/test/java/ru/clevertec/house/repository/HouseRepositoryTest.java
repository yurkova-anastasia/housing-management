package ru.clevertec.house.repository;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import ru.clevertec.house.util.TestContainer;
import ru.clevertec.house.model.House;

import java.util.List;
import java.util.UUID;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
class HouseRepositoryTest extends TestContainer {

    private final TestEntityManager testEntityManager;
    private final HouseRepository houseRepository;

    @Test
    void findAllPreviousResidencyOfPerson() {
        House house = testEntityManager.find(House.class, 1L);
        Page<House> expected = new PageImpl<>(List.of(house));

        Page<House> actual = houseRepository
                .findAllPreviousResidencyOfPerson(UUID.fromString("4eac5a60-63b8-443e-9122-9b455745ae5e"),
                        PageRequest.of(0, 15));

        Assertions.assertEquals(expected.getContent(), actual.getContent());
    }

    @Test
    void findAllPreviousOwnedHousesOfPerson() {
        House house = testEntityManager.find(House.class, 1L);
        Page<House> expected = new PageImpl<>(List.of(house));

        Page<House> actual = houseRepository
                .findAllPreviousOwnedHousesOfPerson(UUID.fromString("4eac5a60-63b8-443e-9122-9b455745ae5e"),
                        PageRequest.of(0, 15));

        Assertions.assertEquals(expected.getContent(), actual.getContent());
    }
}