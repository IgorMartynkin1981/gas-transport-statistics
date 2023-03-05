package ru.alrosa.transport.gastransportstatistics.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import ru.alrosa.transport.gastransportstatistics.exception.Create;
import ru.alrosa.transport.gastransportstatistics.entity.Subdivision;

@Data
public class InfoUserDto {
    @Positive(groups = {Create.class})
    private Long id;
    @NotBlank(groups = {Create.class})
    private String firstName;
    @NotBlank(groups = {Create.class})
    private String lastName;
    @Email(groups = {Create.class})
    private String email;
    private String login;
    private Subdivision subdivision;
}
