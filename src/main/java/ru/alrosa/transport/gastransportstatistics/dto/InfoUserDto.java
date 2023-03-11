package ru.alrosa.transport.gastransportstatistics.dto;


import lombok.Data;
import ru.alrosa.transport.gastransportstatistics.exception.Create;
import ru.alrosa.transport.gastransportstatistics.entity.Subdivision;

import javax.validation.constraints.*;

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
    private String userName;
    private Subdivision subdivision;
}
