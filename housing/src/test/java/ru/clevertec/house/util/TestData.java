package ru.clevertec.house.util;

import lombok.experimental.UtilityClass;
import ru.clevertec.house.dto.request.HouseRequestDto;
import ru.clevertec.house.dto.request.PersonRequestDto;
import ru.clevertec.house.dto.response.HouseResponseDto;
import ru.clevertec.house.dto.response.PersonResponseDto;
import ru.clevertec.house.model.House;
import ru.clevertec.house.model.Person;

import java.util.UUID;

@UtilityClass
public class TestData {


    public static final UUID EXISTED_PERSON_UUID = UUID.fromString("6ac41ec7-fda7-4095-9b49-1c723a1574fa");
    public static final UUID EXISTED_HOUSE_UUID = UUID.fromString("59c89acc-62a4-4cbc-87ed-78f632996c08");

    public static PersonResponseDto getPersonResponseDto() {
        return PersonResponseDto.builder()
                .uuid(EXISTED_PERSON_UUID)
                .build();
    }

    public static PersonResponseDto getPersonResponseDtoWithId3() {
        return PersonResponseDto.builder()
                .uuid(UUID.fromString("05d56950-9c7b-438c-8c3a-531290834e88"))
                .build();
    }

    public static PersonResponseDto getPersonResponseDtoWithId4() {
        return PersonResponseDto.builder()
                .uuid(UUID.fromString("dee20bf9-0358-440b-8dd0-72e4f1eb66bf"))
                .build();
    }

    public static HouseResponseDto getHouseResponseDto() {
        return HouseResponseDto.builder()
                .uuid(EXISTED_HOUSE_UUID)
                .build();
    }

    public static HouseResponseDto getHouseResponseDtoWithId4() {
        return HouseResponseDto.builder()
                .uuid(UUID.fromString("af33aecd-1cdc-4af1-afa3-cbec5b2cdf7b"))
                .build();
    }

    public static PersonRequestDto getPersonRequestDto() {
        return PersonRequestDto.builder()
                .build();
    }

    public static HouseRequestDto getHouseRequestDto() {
        return HouseRequestDto.builder()
                .build();
    }

    public static House getHouse() {
        return House.builder()
                .uuid(EXISTED_HOUSE_UUID)
                .build();
    }

    public static House getHouseWithId2() {
        return House.builder()
                .uuid(UUID.fromString("7d0fed5b-b7d5-461a-988a-1cd52b591d80"))
                .build();
    }

    public static Person getPerson() {
        return Person.builder()
                .uuid(EXISTED_PERSON_UUID)
                .build();
    }

    public static Person getPersonWithId2() {
        return Person.builder()
                .uuid(UUID.fromString("4eac5a60-63b8-443e-9122-9b455745ae5e"))
                .build();
    }

}
