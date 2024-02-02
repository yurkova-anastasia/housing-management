package ru.clevertec.house.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.clevertec.house.dto.request.HouseRequestDto;
import ru.clevertec.house.dto.response.HouseResponseDto;
import ru.clevertec.house.dto.response.PersonResponseDto;
import ru.clevertec.house.facade.HouseServiceFacade;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/houses")
@RequiredArgsConstructor
@Tag(name = "House controller", description = "The House API")
public class HouseController {

    private final HouseServiceFacade houseServiceFacade;

    @PostMapping
    @Operation(summary = "Create new House")
    public ResponseEntity<HttpStatus> create(@RequestBody HouseRequestDto houseRequestDto) {
        houseServiceFacade.create(houseRequestDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Find House by id")
    public HouseResponseDto findById(@PathVariable("id") UUID id) {
        return houseServiceFacade.findById(id);
    }

    @GetMapping()
    @Operation(summary = "Find all houses")
    public ResponseEntity<List<HouseResponseDto>> findAll(@PageableDefault(size = 15) Pageable pageable) {
        return new ResponseEntity<>(houseServiceFacade.findAll(pageable).getContent(), HttpStatus.OK);
    }

    @GetMapping("/{id}/residents")
    @Operation(summary = "Find all residents of a house")
    public ResponseEntity<List<PersonResponseDto>> findHouseResidents(@PathVariable("id") UUID id,
                                                                      @PageableDefault(size = 15) Pageable pageable) {
        List<PersonResponseDto> result = houseServiceFacade.findHouseResidents(id, pageable).getContent();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }


    @PutMapping("/{id}")
    @Operation(summary = "Update house")
    public ResponseEntity<HttpStatus> update(@PathVariable("id") UUID id,
                                             @RequestBody HouseRequestDto houseRequestDto) {
        houseServiceFacade.update(id, houseRequestDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete house")
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") UUID id) {
        houseServiceFacade.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
