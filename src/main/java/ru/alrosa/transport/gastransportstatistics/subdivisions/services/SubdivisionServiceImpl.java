package ru.alrosa.transport.gastransportstatistics.subdivisions.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.alrosa.transport.gastransportstatistics.exception.DataNotFound;
import ru.alrosa.transport.gastransportstatistics.exception.ValidationDataException;
import ru.alrosa.transport.gastransportstatistics.subdivisions.dto.SubdivisionDto;
import ru.alrosa.transport.gastransportstatistics.subdivisions.dto.SubdivisionMapper;
import ru.alrosa.transport.gastransportstatistics.subdivisions.model.Subdivision;
import ru.alrosa.transport.gastransportstatistics.subdivisions.repositories.SubdivisionRepository;

import javax.validation.Valid;
import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class SubdivisionServiceImpl implements SubdivisionService {

    private final SubdivisionRepository subdivisionRepository;

    @Autowired
    public SubdivisionServiceImpl(SubdivisionRepository subdivisionRepository) {
        this.subdivisionRepository = subdivisionRepository;
    }

    @Override
    public Collection<SubdivisionDto> getAllSubdivision() {
        return subdivisionRepository.findAll().stream().map(SubdivisionMapper::toSubdivisionDto)
                .collect(Collectors.toList());
    }

    @Override
    public SubdivisionDto getSubdivisionById(@Valid Long subdivisionDtoId) {
        return SubdivisionMapper.toSubdivisionDto(verifySubdivisionInRepository(subdivisionDtoId));
    }

    @Override
    public SubdivisionDto createSubdivision(@Valid SubdivisionDto subdivisionDto) {
        return SubdivisionMapper.toSubdivisionDto(
                subdivisionRepository.save(
                        SubdivisionMapper.toSubdivision(subdivisionDto)
                )
        );
    }

    @Override
    public SubdivisionDto updateSubdivision(SubdivisionDto subdivisionDto) {
        {
            verifySubdivisionInRepository(subdivisionDto.getId());
        }
        return SubdivisionMapper.toSubdivisionDto(
                subdivisionRepository.save(
                        SubdivisionMapper.toSubdivision(subdivisionDto)
                )
        );
    }

    @Override
    public void deleteSubdivisionById(Long subdivisionDtoId) {
        {
            verifySubdivisionInRepository(subdivisionDtoId);
        }
        subdivisionRepository.deleteById(subdivisionDtoId);
    }

    private Subdivision verifySubdivisionInRepository(Long subdivisionId) {
        if (subdivisionId == null || subdivisionId < 0) {
            throw new ValidationDataException("invalid id, id must be greater than 0");
        }
        return subdivisionRepository.findById(subdivisionId).orElseThrow(() -> new DataNotFound(
                        String.format("User with id %d was not found in the database", subdivisionId
                        )
                )
        );
    }
}
