package ru.alrosa.transport.gastransportstatistics.dto;

import lombok.Data;
import ru.alrosa.transport.gastransportstatistics.entity.Role;
import ru.alrosa.transport.gastransportstatistics.entity.Status;

import java.util.Collection;

@Data
public class UserDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String userName;
    private String password;
    private Collection<Role> roles;
    private Status status;
    private Long subdivisionId;
}
