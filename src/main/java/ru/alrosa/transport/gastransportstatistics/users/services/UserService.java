package ru.alrosa.transport.gastransportstatistics.users.services;

import ru.alrosa.transport.gastransportstatistics.users.dto.InfoUserDto;
import ru.alrosa.transport.gastransportstatistics.users.dto.UserDto;

import java.util.Collection;

public interface UserService {
    Collection<InfoUserDto> getAllUsers();

    InfoUserDto getUserById(Long userId);

    InfoUserDto updateUser(UserDto userDto);

    InfoUserDto createUser(UserDto userDto);

    void deleteUserById(Long userId);
}
