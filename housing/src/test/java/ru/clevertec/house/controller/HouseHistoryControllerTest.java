package ru.clevertec.house.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.clevertec.house.dto.response.HouseResponseDto;
import ru.clevertec.house.dto.response.PersonResponseDto;
import ru.clevertec.house.facade.HouseHistoryServiceFacade;

import java.util.List;

import static org.mockito.Mockito.doReturn;
import static org.springframework.data.domain.PageRequest.of;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.clevertec.house.util.TestData.EXISTED_HOUSE_UUID;
import static ru.clevertec.house.util.TestData.EXISTED_PERSON_UUID;
import static ru.clevertec.house.util.TestData.getHouseResponseDto;
import static ru.clevertec.house.util.TestData.getPersonResponseDto;

@WebMvcTest(HouseHistoryController.class)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
class HouseHistoryControllerTest {

    private final MockMvc mockMvc;

    @MockBean
    private final HouseHistoryServiceFacade houseHistoryServiceFacade;

    @Test
    void findAllPreviousResidentsOfHouse() throws Exception {
        PersonResponseDto personResponseDto = getPersonResponseDto();
        PageImpl<PersonResponseDto> page = new PageImpl<>(List.of(personResponseDto));
        doReturn(page)
                .when(houseHistoryServiceFacade).findAllPreviousResidentsOfHouse(EXISTED_HOUSE_UUID, of(0, 15));

        mockMvc.perform(get("/house-history/{houseId}/previous-residents", EXISTED_HOUSE_UUID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].uuid").value(EXISTED_PERSON_UUID.toString()));
    }

    @Test
    void findAllPreviousOwnersOfHouse() throws Exception {
        PersonResponseDto personResponseDto = getPersonResponseDto();
        PageImpl<PersonResponseDto> page = new PageImpl<>(List.of(personResponseDto));
        doReturn(page)
                .when(houseHistoryServiceFacade).findAllPreviousOwnersOfHouse(EXISTED_HOUSE_UUID, of(0, 15));

        mockMvc.perform(get("/house-history/{houseId}/previous-owners", EXISTED_HOUSE_UUID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].uuid").value(EXISTED_PERSON_UUID.toString()));
    }

    @Test
    void findAllPreviousResidencyOfPerson() throws Exception {
        HouseResponseDto houseResponseDto = getHouseResponseDto();
        PageImpl<HouseResponseDto> page = new PageImpl<>(List.of(houseResponseDto));
        doReturn(page)
                .when(houseHistoryServiceFacade).findAllPreviousResidencyOfPerson(EXISTED_PERSON_UUID, of(0, 15));

        mockMvc.perform(get("/house-history/{personId}/previous-residency", EXISTED_PERSON_UUID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].uuid").value(EXISTED_HOUSE_UUID.toString()));
    }

    @Test
    void findAllPreviousOwnedHousesOfPerson() throws Exception {
        HouseResponseDto houseResponseDto = getHouseResponseDto();
        PageImpl<HouseResponseDto> page = new PageImpl<>(List.of(houseResponseDto));
        doReturn(page)
                .when(houseHistoryServiceFacade).findAllPreviousOwnedHousesOfPerson(EXISTED_PERSON_UUID, of(0, 15));

        mockMvc.perform(get("/house-history/{personId}/previous-owned-houses", EXISTED_PERSON_UUID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].uuid").value(EXISTED_HOUSE_UUID.toString()));
    }
}