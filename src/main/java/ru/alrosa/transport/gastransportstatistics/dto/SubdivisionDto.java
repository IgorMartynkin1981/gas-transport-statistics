package ru.alrosa.transport.gastransportstatistics.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SubdivisionDto {
    @Positive
    private Long id;
    @NotBlank
    private String subdivisionName;
    private String subdivisionFullName;
}
