package ru.alrosa.transport.gastransportstatistics.users.services;

import ru.alrosa.transport.gastransportstatistics.users.dto.UserDto;

import java.util.Collection;

public interface UserService {
    Collection<UserDto> getAllUsers();

    UserDto getUserById(Long userId);

    UserDto updateUser(Long userId, UserDto userDto);

    UserDto createUser(UserDto userDto);

    void deleteUserById(Long userId);
}
