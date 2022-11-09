package ru.alrosa.transport.gastransportstatistics.users.dto;

import lombok.Data;
import ru.alrosa.transport.gastransportstatistics.exception.Create;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class UserDto {
    private Long id;
    @NotBlank(groups = {Create.class})
    private String firstName;
    @NotBlank(groups = {Create.class})
    private String lastName;
    @Email(groups = {Create.class})
    private String email;
    private String login;
    private Long subdivisionId;
}
