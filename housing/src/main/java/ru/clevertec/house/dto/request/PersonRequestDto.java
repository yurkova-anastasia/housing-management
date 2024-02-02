package ru.clevertec.house.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PersonRequestDto {

    private String name;
    private String surname;
    private String sex;
    private String passportSeries;
    private String passportNumber;
    private UUID residencyId;

}
