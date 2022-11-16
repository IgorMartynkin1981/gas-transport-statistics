package ru.alrosa.transport.gastransportstatistics.subdivisions.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.alrosa.transport.gastransportstatistics.exception.Create;
import ru.alrosa.transport.gastransportstatistics.subdivisions.dto.SubdivisionDto;
import ru.alrosa.transport.gastransportstatistics.subdivisions.services.SubdivisionService;

import java.util.Collection;

@RestController
@RequestMapping(path = "/subdivisions")
public class SubdivisionController {
    private final SubdivisionService subdivisionService;

    @Autowired
    public SubdivisionController(SubdivisionService subdivisionService) {
        this.subdivisionService = subdivisionService;
    }

    @GetMapping()
    public Collection<SubdivisionDto> getSubdivisionDto() {
        return subdivisionService.getAllSubdivision();
    }

    @GetMapping("/{id}")
    public SubdivisionDto getSubdivisionDtoById(@PathVariable Long id) {
        return subdivisionService.getSubdivisionById(id);
    }

    @PostMapping
    public SubdivisionDto createSubdivisionDto(@Validated({Create.class}) @RequestBody SubdivisionDto subdivisionDto) {
        return subdivisionService.createSubdivision(subdivisionDto);
    }

    @PatchMapping("/{id}")
    public SubdivisionDto updateUser(@RequestBody SubdivisionDto subdivisionDto) {
        return subdivisionService.updateSubdivision(subdivisionDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUserById(@PathVariable Long id) {
        subdivisionService.deleteSubdivisionById(id);
        return new ResponseEntity<>(String.format("Subdivision with ID: %s was deleted!", id), HttpStatus.OK);
    }

}
