package ru.alrosa.transport.gastransportstatistics.services;

import ru.alrosa.transport.gastransportstatistics.dto.InfoUserDto;
import ru.alrosa.transport.gastransportstatistics.dto.UserAuthentication;
import ru.alrosa.transport.gastransportstatistics.entity.User;

import java.util.Collection;

public interface UserService {
    Collection<InfoUserDto> getAllUsers();

    InfoUserDto getUserById(Long userId);

    User findByUsername(String username);

    InfoUserDto updateUser(UserAuthentication userAuthentication);

    InfoUserDto createUser(UserAuthentication userAuthentication);

    void deactivationUser(Long userId);

    void deleteUserById(Long userId);
}
