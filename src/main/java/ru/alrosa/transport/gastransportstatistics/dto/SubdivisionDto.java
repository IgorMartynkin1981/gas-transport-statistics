package ru.alrosa.transport.gastransportstatistics.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

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
