package ru.alrosa.transport.gastransportstatistics.services;

import ru.alrosa.transport.gastransportstatistics.dto.InfoUserDto;
import ru.alrosa.transport.gastransportstatistics.dto.UserAuthentication;
import ru.alrosa.transport.gastransportstatistics.dto.UserDto;
import ru.alrosa.transport.gastransportstatistics.entity.User;

import java.util.Collection;

public interface UserService {
    Collection<InfoUserDto> getAllUsers();

    InfoUserDto getUserById(Long userId);

    User findByUsername(String username);

    InfoUserDto updateUser(UserDto userDto);

    InfoUserDto createUser(UserAuthentication userAuthentication);

    void deleteUserById(Long userId);
}
