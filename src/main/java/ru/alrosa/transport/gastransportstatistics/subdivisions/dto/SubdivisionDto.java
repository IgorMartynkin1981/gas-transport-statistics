package ru.alrosa.transport.gastransportstatistics.subdivisions.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
@NoArgsConstructor
public class SubdivisionDto {
    @Positive
    private Long id;
    @NotBlank
    @NotEmpty
    @NotNull
    private String subdivisionName;
    private String subdivisionFullName;
}
