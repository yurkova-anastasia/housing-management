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
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.clevertec.house.dto.request.PersonRequestDto;
import ru.clevertec.house.dto.response.HouseResponseDto;
import ru.clevertec.house.dto.response.PersonResponseDto;
import ru.clevertec.house.facade.PersonServiceFacade;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/people")
@RequiredArgsConstructor
@Tag(name = "Person controller", description = "The Person API")
public class PersonController {

    private final PersonServiceFacade personServiceFacade;

    @PostMapping
    @Operation(summary = "Create new Person")
    public ResponseEntity<HttpStatus> create(@RequestBody PersonRequestDto personRequestDto) {
        personServiceFacade.create(personRequestDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Find Person by id")
    public ResponseEntity<PersonResponseDto> findById(@PathVariable("id") UUID id) {
        return new ResponseEntity<>(personServiceFacade.findById(id), HttpStatus.OK);
    }

    @GetMapping()
    @Operation(summary = "Find all people")
    public ResponseEntity<List<PersonResponseDto>> findAll(@PageableDefault(size = 15) Pageable pageable) {
        return new ResponseEntity<>(personServiceFacade.findAll(pageable).getContent(), HttpStatus.OK);
    }

    @GetMapping("/{id}/owned-houses")
    @Operation(summary = "Find all houses that a person owns")
    public ResponseEntity<List<HouseResponseDto>> findOwnedHouses(@PathVariable("id") UUID id,
                                                                  @PageableDefault(size = 15) Pageable pageable) {
        List<HouseResponseDto> result = personServiceFacade.findOwnedHouses(id, pageable).getContent();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update person")
    public ResponseEntity<HttpStatus> update(@PathVariable("id") UUID id,
                                             @RequestBody PersonRequestDto personRequestDto) {
        personServiceFacade.update(id, personRequestDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("{id}/owned-houses")
    @Operation(summary = "Add a new house that a person owns")
    public ResponseEntity<HttpStatus> addOwnedHouse(@PathVariable("id") UUID id,
                                                    @RequestParam UUID houseId) {
        personServiceFacade.addOwnedHouse(id, houseId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("{id}/-owned-houses")
    @Operation(summary = "Remove a house from person ownership")
    public ResponseEntity<HttpStatus> removeOwnedHouse(@PathVariable("id") UUID id,
                                                       @RequestParam UUID houseId) {
        personServiceFacade.removeOwnedHouse(id, houseId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete person")
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") UUID id) {
        personServiceFacade.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
