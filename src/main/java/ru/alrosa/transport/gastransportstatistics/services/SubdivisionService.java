package ru.alrosa.transport.gastransportstatistics.services;

import ru.alrosa.transport.gastransportstatistics.dto.SubdivisionDto;

import java.util.Collection;

public interface SubdivisionService {

    Collection<SubdivisionDto> getAllSubdivision();

    SubdivisionDto getSubdivisionById(Long subdivisionDtoId);

    SubdivisionDto updateSubdivision(SubdivisionDto subdivisionDto);

    SubdivisionDto createSubdivision(SubdivisionDto subdivisionDto);

    void deleteSubdivisionById(Long subdivisionDtoId);
}
