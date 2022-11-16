package ru.alrosa.transport.gastransportstatistics.users.dto;

import lombok.Data;

@Data
public class UserDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String login;
    private Long subdivisionId;
}
