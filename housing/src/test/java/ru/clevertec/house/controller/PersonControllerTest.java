package ru.clevertec.house.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.clevertec.house.dto.request.PersonRequestDto;
import ru.clevertec.house.dto.response.HouseResponseDto;
import ru.clevertec.house.dto.response.PersonResponseDto;
import ru.clevertec.house.facade.PersonServiceFacade;

import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.doReturn;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.clevertec.house.util.TestData.EXISTED_HOUSE_UUID;
import static ru.clevertec.house.util.TestData.EXISTED_PERSON_UUID;
import static ru.clevertec.house.util.TestData.getHouseResponseDto;
import static ru.clevertec.house.util.TestData.getHouseResponseDtoWithId4;
import static ru.clevertec.house.util.TestData.getPersonRequestDto;
import static ru.clevertec.house.util.TestData.getPersonResponseDto;
import static ru.clevertec.house.util.TestData.getPersonResponseDtoWithId3;
import static ru.clevertec.house.util.TestData.getPersonResponseDtoWithId4;

@WebMvcTest(PersonController.class)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
class PersonControllerTest {

    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;

    @MockBean
    private final PersonServiceFacade personServiceFacade;


    @Test
    @SneakyThrows
    void create() {
        PersonRequestDto personRequestDto = getPersonRequestDto();
        String content = objectMapper.writeValueAsString(personRequestDto);

        mockMvc.perform(post("/people")
                        .contentType(APPLICATION_JSON)
                        .content(content))
                .andExpect(status().isOk());
    }

    @Test
    @SneakyThrows
    void findById() {
        PersonResponseDto personResponseDto = getPersonResponseDto();
        doReturn(personResponseDto)
                .when(personServiceFacade).findById(EXISTED_PERSON_UUID);

        mockMvc.perform(get("/people/{id}", EXISTED_PERSON_UUID)
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.uuid").value(EXISTED_PERSON_UUID.toString()));
    }

    @Test
    void findAll() throws Exception {
        PersonResponseDto personResponseDto = getPersonResponseDtoWithId3();
        PersonResponseDto personResponseDto2 = getPersonResponseDtoWithId4();
        Page<PersonResponseDto> page = new PageImpl<>(List.of(personResponseDto, personResponseDto2));
        doReturn(page)
                .when(personServiceFacade).findAll(PageRequest.of(0, 15));

        mockMvc.perform(get("/people")
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].uuid").value(personResponseDto.uuid().toString()))
                .andExpect(jsonPath("$[1].uuid").value(personResponseDto2.uuid().toString()));
    }

    @Test
    void findOwnedHouses() throws Exception {
        HouseResponseDto houseResponseDto = getHouseResponseDto();
        HouseResponseDto houseResponseDto2 = getHouseResponseDtoWithId4();
        PageImpl<HouseResponseDto> page = new PageImpl<>(List.of(houseResponseDto, houseResponseDto2));
        doReturn(page)
                .when(personServiceFacade).findOwnedHouses(EXISTED_PERSON_UUID, PageRequest.of(0, 15));

        mockMvc.perform(get("/people/{id}/owned-houses", EXISTED_PERSON_UUID)
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].uuid").value(EXISTED_HOUSE_UUID.toString()))
                .andExpect(jsonPath("$[1].uuid").value("af33aecd-1cdc-4af1-afa3-cbec5b2cdf7b"));
    }

    @Test
    void update() throws Exception {
        PersonRequestDto updatedPersonRequestDto = getPersonRequestDto();

        mockMvc.perform(put("/people/{id}", EXISTED_PERSON_UUID)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedPersonRequestDto)))
                .andExpect(status().isOk());
    }

    @Test
    void addOwnedHouse() throws Exception {
        UUID houseId = UUID.randomUUID();

        mockMvc.perform(patch("/people/{id}/owned-houses", EXISTED_PERSON_UUID)
                        .param("houseId", houseId.toString())
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void removeOwnedHouse() throws Exception {
        UUID houseId = UUID.randomUUID();

        mockMvc.perform(MockMvcRequestBuilders.delete("/people/{id}/-owned-houses", EXISTED_PERSON_UUID)
                        .param("houseId", houseId.toString())
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void delete() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/people/{id}", EXISTED_PERSON_UUID)
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}