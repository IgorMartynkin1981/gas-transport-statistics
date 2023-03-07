package ru.alrosa.transport.gastransportstatistics.security;

import lombok.Data;

/**
 * DTO class for authentication (login) request.
 *
 * @author Igor Martynkin
 * @version 1.0
 */

@Data
public class AuthenticationRequestDto {
    private String username;
    private String password;
}
