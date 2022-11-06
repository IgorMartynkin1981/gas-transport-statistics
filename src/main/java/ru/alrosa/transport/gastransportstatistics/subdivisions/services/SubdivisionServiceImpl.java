package ru.alrosa.transport.gastransportstatistics.subdivisions.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.alrosa.transport.gastransportstatistics.exception.DataNotFound;
import ru.alrosa.transport.gastransportstatistics.subdivisions.dto.SubdivisionDto;
import ru.alrosa.transport.gastransportstatistics.subdivisions.dto.SubdivisionMapper;
import ru.alrosa.transport.gastransportstatistics.subdivisions.repositories.SubdivisionRepository;

import java.util.Collection;

@Service
public class SubdivisionServiceImpl implements SubdivisionService {

    private final SubdivisionRepository subdivisionRepository;

    @Autowired
    public SubdivisionServiceImpl(SubdivisionRepository subdivisionRepository) {
        this.subdivisionRepository = subdivisionRepository;
    }

    @Override
    public Collection<SubdivisionDto> getAllSubdivisionDto() {
        return subdivisionRepository.findAll().stream().map(SubdivisionMapper::toSubdivisionDto).toList();
    }

    @Override
    public SubdivisionDto getSubdivisionDtoById(Long subdivisionDtoId) {
        return SubdivisionMapper.toSubdivisionDto(
                subdivisionRepository.findById(subdivisionDtoId).orElseThrow(() -> new DataNotFound(
                                String.format("User with id %d was not found in the database", subdivisionDtoId
                                )
                        )
                )
        );
    }

    @Override
    public SubdivisionDto updateSubdivisionDto(SubdivisionDto subdivisionDto) {
        return SubdivisionMapper.toSubdivisionDto(
                subdivisionRepository.save(
                        SubdivisionMapper.toSubdivision(subdivisionDto)
                )
        );
    }

    @Override
    public SubdivisionDto createSubdivisionDto(SubdivisionDto subdivisionDto) {
        return null;
    }

    @Override
    public void deleteSubdivisionDtoById(Long subdivisionDtoId) {

    }
}
