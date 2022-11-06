package ru.alrosa.transport.gastransportstatistics.users.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.alrosa.transport.gastransportstatistics.exception.DataNotFound;
import ru.alrosa.transport.gastransportstatistics.users.dto.UserDto;
import ru.alrosa.transport.gastransportstatistics.users.dto.UserMapper;
import ru.alrosa.transport.gastransportstatistics.users.model.User;
import ru.alrosa.transport.gastransportstatistics.users.repositories.UserRepository;


import javax.validation.Valid;
import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Collection<UserDto> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(UserMapper::toUserDto)
                .toList();
    }

    @Override
    public UserDto getUserById(Long userId) {
        return UserMapper.toUserDto(findAndVerifyUserInRepository(userId));
    }

    @Override
    public UserDto updateUser(Long userId, UserDto userDto) {
        User user = findAndVerifyUserInRepository(userId);

        return UserMapper.toUserDto(userRepository.save(updateUserFromData(
                UserMapper.toUser(userDto), user)));
    }

    private User findAndVerifyUserInRepository(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new DataNotFound(
                String.format("User with id %d was not found in the database", userId)));
    }

    @Override
    public UserDto createUser(@Valid UserDto userDto) {
        return UserMapper.toUserDto(userRepository.save(UserMapper.toUser(userDto)));
    }

    @Override
    public void deleteUserById(Long userId) {
        userRepository.deleteById(userId);
    }

    private User updateUserFromData(User user, User userFromData) {
        if (user.getFirstName() != null) {
            userFromData.setFirstName(user.getFirstName());
        }
        if (user.getLastName() != null) {
            userFromData.setLastName(user.getLastName());
        }
        if (user.getEmail() != null) {
            userFromData.setEmail(user.getEmail());
        }
        if (user.getLogin() != null) {
            userFromData.setLogin(user.getLogin());
        }
        if (user.getSubdivisionId() != null) {
            userFromData.setSubdivisionId(user.getSubdivisionId());
        }
        return userFromData;
    }
}