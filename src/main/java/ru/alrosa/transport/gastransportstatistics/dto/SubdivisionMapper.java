package ru.alrosa.transport.gastransportstatistics.dto;

import ru.alrosa.transport.gastransportstatistics.entity.Subdivision;

public class SubdivisionMapper {

    public static SubdivisionDto toSubdivisionDto(Subdivision subdivision) {
        SubdivisionDto subdivisionDto = new SubdivisionDto();
        subdivisionDto.setId(subdivision.getId());
        subdivisionDto.setSubdivisionName(subdivision.getSubdivisionName());
        subdivisionDto.setSubdivisionFullName(subdivision.getSubdivisionFullName());

        return subdivisionDto;
    }

    public static Subdivision toSubdivision(SubdivisionDto subdivisionDto) {
        Subdivision subdivision = new Subdivision();
        if (subdivisionDto.getId() != null) {
            subdivision.setId(subdivisionDto.getId());
        }
        subdivision.setSubdivisionName(subdivisionDto.getSubdivisionName());
        subdivision.setSubdivisionFullName(subdivisionDto.getSubdivisionFullName());

        return subdivision;
    }
}
