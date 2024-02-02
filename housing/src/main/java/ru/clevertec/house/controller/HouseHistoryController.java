package ru.clevertec.house.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.clevertec.house.dto.response.HouseResponseDto;
import ru.clevertec.house.dto.response.PersonResponseDto;
import ru.clevertec.house.facade.HouseHistoryServiceFacade;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/house-history")
@RequiredArgsConstructor
@Tag(name = "House history controller", description = "The House history API")
public class HouseHistoryController {

    private final HouseHistoryServiceFacade houseHistoryServiceFacade;

    @GetMapping("{houseId}/previous-residents")
    @Operation(summary = "Find all previous tenants of house")
    public ResponseEntity<List<PersonResponseDto>> findAllPreviousResidentsOfHouse(@PathVariable("houseId") UUID houseId,
                                                                                         @PageableDefault(size = 15) Pageable pageable) {
        List<PersonResponseDto> result = houseHistoryServiceFacade.findAllPreviousResidentsOfHouse(houseId, pageable).getContent();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("{houseId}/previous-owners")
    @Operation(summary = "Find all previous owners of house")
    public ResponseEntity<List<PersonResponseDto>> findAllPreviousOwnersOfHouse(@PathVariable("houseId") UUID houseId,
                                                                                @PageableDefault(size = 15) Pageable pageable) {
        List<PersonResponseDto> result = houseHistoryServiceFacade.findAllPreviousOwnersOfHouse(houseId, pageable).getContent();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("{personId}/previous-residency")
    @Operation(summary = "Find all the previous houses where the person lived")
    public ResponseEntity<List<HouseResponseDto>> findAllPreviousResidencyOfPerson(@PathVariable("personId") UUID personId,
                                                                                   @PageableDefault(size = 15) Pageable pageable) {
        List<HouseResponseDto> result = houseHistoryServiceFacade.findAllPreviousResidencyOfPerson(personId, pageable).getContent();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("{personId}/previous-owned-houses")
    @Operation(summary = "Find all the previous houses that the person owned")
    public ResponseEntity<List<HouseResponseDto>> findAllPreviousOwnedHousesOfPerson(@PathVariable("personId") UUID personId,
                                                                                     @PageableDefault(size = 15) Pageable pageable) {
        List<HouseResponseDto> result = houseHistoryServiceFacade.findAllPreviousOwnedHousesOfPerson(personId, pageable).getContent();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }


}
