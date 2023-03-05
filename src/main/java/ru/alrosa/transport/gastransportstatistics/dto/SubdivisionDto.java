package ru.alrosa.transport.gastransportstatistics.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;

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
