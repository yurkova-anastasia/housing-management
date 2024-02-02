package ru.clevertec.house.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.clevertec.house.dto.request.HouseRequestDto;
import ru.clevertec.house.dto.response.HouseResponseDto;
import ru.clevertec.house.dto.response.PersonResponseDto;
import ru.clevertec.house.facade.HouseServiceFacade;

import java.util.List;

import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.clevertec.house.util.TestData.EXISTED_HOUSE_UUID;
import static ru.clevertec.house.util.TestData.EXISTED_PERSON_UUID;
import static ru.clevertec.house.util.TestData.getHouseRequestDto;
import static ru.clevertec.house.util.TestData.getHouseResponseDto;
import static ru.clevertec.house.util.TestData.getHouseResponseDtoWithId4;
import static ru.clevertec.house.util.TestData.getPersonResponseDto;
import static ru.clevertec.house.util.TestData.getPersonResponseDtoWithId4;

@WebMvcTest(HouseController.class)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
class HouseControllerTest {

    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;
    @MockBean
    private final HouseServiceFacade houseServiceFacade;

    @Test
    void create() throws Exception {
        HouseRequestDto houseRequestDto = getHouseRequestDto();
        String content = objectMapper.writeValueAsString(houseRequestDto);

        mockMvc.perform(post("/houses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(status().isOk());
    }

    @Test
    void findById() throws Exception {
        HouseResponseDto houseResponseDto = getHouseResponseDto();
        doReturn(houseResponseDto)
                .when(houseServiceFacade).findById(EXISTED_HOUSE_UUID);

        mockMvc.perform(get("/houses/{id}", EXISTED_HOUSE_UUID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.uuid").value(EXISTED_HOUSE_UUID.toString()));
    }

    @Test
    void findAll() throws Exception {
        HouseResponseDto houseResponseDto1 = getHouseResponseDto();
        HouseResponseDto houseResponseDto2 = getHouseResponseDtoWithId4();
        Page<HouseResponseDto> page = new PageImpl<>(List.of(houseResponseDto1, houseResponseDto2));
        doReturn(page)
                .when(houseServiceFacade).findAll(PageRequest.of(0, 15));

        mockMvc.perform(get("/houses")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].uuid").value(houseResponseDto1.uuid().toString()))
                .andExpect(jsonPath("$[1].uuid").value(houseResponseDto2.uuid().toString()));
    }

    @Test
    void findHouseResidents() throws Exception {
        PersonResponseDto personResponseDto = getPersonResponseDto();
        PersonResponseDto personResponseDto2 = getPersonResponseDtoWithId4();
        PageImpl<PersonResponseDto> page = new PageImpl<>(List.of(personResponseDto, personResponseDto2));
        doReturn(page)
                .when(houseServiceFacade).findHouseResidents(EXISTED_HOUSE_UUID, PageRequest.of(0, 15));

        mockMvc.perform(get("/houses/{id}/residents", EXISTED_HOUSE_UUID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].uuid").value(EXISTED_PERSON_UUID.toString()))
                .andExpect(jsonPath("$[1].uuid").value(personResponseDto2.uuid().toString()));
    }

    @Test
    void update() throws Exception {
        HouseRequestDto updatedHouseRequestDto = getHouseRequestDto();

        mockMvc.perform(put("/houses/{id}", EXISTED_HOUSE_UUID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedHouseRequestDto)))
                .andExpect(status().isOk());
    }

    @Test
    void delete() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/houses/{id}", EXISTED_HOUSE_UUID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}