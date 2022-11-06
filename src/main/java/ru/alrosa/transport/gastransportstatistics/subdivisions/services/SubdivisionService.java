package ru.alrosa.transport.gastransportstatistics.subdivisions.services;

import ru.alrosa.transport.gastransportstatistics.subdivisions.dto.SubdivisionDto;

import java.util.Collection;

public interface SubdivisionService {

    Collection<SubdivisionDto> getAllSubdivisionDto();

    SubdivisionDto getSubdivisionDtoById(Long subdivisionDtoId);

    SubdivisionDto updateSubdivisionDto(SubdivisionDto subdivisionDto);

    SubdivisionDto createSubdivisionDto(SubdivisionDto subdivisionDto);

    void deleteSubdivisionDtoById(Long subdivisionDtoId);
}
