package ru.alrosa.transport.gastransportstatistics.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import ru.alrosa.transport.gastransportstatistics.entity.Role;
import ru.alrosa.transport.gastransportstatistics.entity.Status;

import java.time.LocalDateTime;
import java.util.Collection;

@Data
public class UserAuthentication {

    private Long id;

    @NotBlank
    private String username;

    @NotBlank
    private String firstName;

    private String lastName;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Size(min = 8, max = 16, message = "Пароль должен содержать от 8 до 16 символов")
    private String password;

    private Long subdivisionId;

    private Collection<Role> roles;

    private LocalDateTime created;

    private LocalDateTime updated;

    private Status status;
}
