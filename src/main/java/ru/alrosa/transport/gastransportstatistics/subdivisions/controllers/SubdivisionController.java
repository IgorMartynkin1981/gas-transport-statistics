package ru.alrosa.transport.gastransportstatistics.subdivisions.controllers;

import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping("/{id}")
    public SubdivisionDto getSubdivisionDtoById(@PathVariable Long id) {
        return subdivisionService.getSubdivisionDtoById(id);
    }

    @GetMapping()
    public Collection<SubdivisionDto> getSubdivisionDtoById() {
        return subdivisionService.getAllSubdivisionDto();
    }

    @PostMapping
    public SubdivisionDto createSubdivisionDto(@Validated({Create.class}) @RequestBody SubdivisionDto subdivisionDto) {
        return subdivisionService.createSubdivisionDto(subdivisionDto);
    }

    @PatchMapping("/{id}")
    public SubdivisionDto updateUser(@RequestBody SubdivisionDto subdivisionDto) {
        return subdivisionService.updateSubdivisionDto(subdivisionDto);
    }

    @DeleteMapping("/{id}")
    public void deleteUserById(@PathVariable Long id) {
        subdivisionService.deleteSubdivisionDtoById(id);
    }

}
