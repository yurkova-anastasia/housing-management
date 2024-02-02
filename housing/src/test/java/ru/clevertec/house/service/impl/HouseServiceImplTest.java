package ru.clevertec.house.service.impl;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import ru.clevertec.house.util.TestContainer;
import ru.clevertec.house.model.House;
import ru.clevertec.house.repository.HouseRepository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static ru.clevertec.house.util.TestData.EXISTED_HOUSE_UUID;
import static ru.clevertec.house.util.TestData.getHouse;
import static ru.clevertec.house.util.TestData.getHouseWithId2;

@SpringBootTest
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
class HouseServiceImplTest extends TestContainer {

    private final HouseServiceImpl houseService;

    @MockBean
    private HouseRepository houseRepository;

    @Test
    void create() {
        House house = getHouse();
        doReturn(house)
                .when(houseRepository).save(house);

        assertThatCode(() -> houseService.create(house))
                .doesNotThrowAnyException();
    }

    @Test
    void findById() {
        House expected = getHouseWithId2();
        doReturn(Optional.of(expected))
                .when(houseRepository).findByUuid(expected.getUuid());

        House actual = houseService.findById(expected.getUuid());

        assertEquals(expected, actual);
    }

    @Test
    void findHousesByOwner() {
        House house = getHouse();
        Page<House> expectedPage = new PageImpl<>(List.of(house));
        doReturn(expectedPage)
                .when(houseRepository).findHousesByOwners_Uuid(EXISTED_HOUSE_UUID, PageRequest.of(0, 15));

        Page<House> actualPage = houseService.findHousesByOwner(EXISTED_HOUSE_UUID, PageRequest.of(0, 15));

        assertThat(actualPage).isEqualTo(expectedPage);
    }

    @Test
    void findAll() {
        House house1 = getHouse();
        House house2 = getHouseWithId2();
        Page<House> expectedPage = new PageImpl<>(List.of(house1, house2));
        doReturn(expectedPage)
                .when(houseRepository).findAll(PageRequest.of(0, 15));

        Page<House> actualPage = houseService.findAll(PageRequest.of(0, 15));

        assertThat(actualPage).isEqualTo(expectedPage);
    }

    @Test
    void findAllPreviousResidencyOfPerson() {
        House house = getHouse();
        Page<House> expectedPage = new PageImpl<>(List.of(house));
        doReturn(expectedPage)
                .when(houseRepository).findAllPreviousResidencyOfPerson(EXISTED_HOUSE_UUID, PageRequest.of(0, 15));

        Page<House> actualPage = houseService.findAllPreviousResidencyOfPerson(EXISTED_HOUSE_UUID, PageRequest.of(0, 15));

        assertThat(actualPage).isEqualTo(expectedPage);
    }

    @Test
    void findAllPreviousOwnedHousesOfPerson() {
        House house = getHouse();
        Page<House> expectedPage = new PageImpl<>(List.of(house));
        doReturn(expectedPage)
                .when(houseRepository).findAllPreviousOwnedHousesOfPerson(EXISTED_HOUSE_UUID, PageRequest.of(0, 15));

        Page<House> actualPage = houseService.findAllPreviousOwnedHousesOfPerson(EXISTED_HOUSE_UUID, PageRequest.of(0, 15));

        assertThat(actualPage).isEqualTo(expectedPage);
    }

    @Test
    void update() {
        House existingHouse = getHouse();
        House updatedHouse = getHouse();
        updatedHouse.setCity("new");
        doReturn(Optional.of(existingHouse))
                .when(houseRepository).findByUuid(EXISTED_HOUSE_UUID);
        doReturn(updatedHouse)
                .when(houseRepository).save(existingHouse);

        House result = houseService.update(EXISTED_HOUSE_UUID, updatedHouse);

        assertEquals(updatedHouse.getCity(), result.getCity());
        verify(houseRepository).findByUuid(EXISTED_HOUSE_UUID);
        verify(houseRepository).save(existingHouse);
    }

    @Test
    void deleteById() {
        assertThatCode(() -> houseService.deleteById(EXISTED_HOUSE_UUID))
                .doesNotThrowAnyException();

        verify(houseRepository).deleteByUuid(EXISTED_HOUSE_UUID);
    }
}