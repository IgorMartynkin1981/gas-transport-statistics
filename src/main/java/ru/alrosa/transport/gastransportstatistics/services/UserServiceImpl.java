package ru.alrosa.transport.gastransportstatistics.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.alrosa.transport.gastransportstatistics.exception.DataNotFound;
import ru.alrosa.transport.gastransportstatistics.entity.Subdivision;
import ru.alrosa.transport.gastransportstatistics.repositories.SubdivisionRepository;
import ru.alrosa.transport.gastransportstatistics.dto.InfoUserDto;
import ru.alrosa.transport.gastransportstatistics.dto.UserDto;
import ru.alrosa.transport.gastransportstatistics.dto.UserMapper;
import ru.alrosa.transport.gastransportstatistics.entity.User;
import ru.alrosa.transport.gastransportstatistics.repositories.UserRepository;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final SubdivisionRepository subdivisionRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, SubdivisionRepository subdivisionRepository) {
        this.userRepository = userRepository;
        this.subdivisionRepository = subdivisionRepository;
    }

    @Override
    public Collection<InfoUserDto> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(UserMapper::toInfoUserDto)
                .collect(Collectors.toList());
    }

    @Override
    public InfoUserDto getUserById(Long userId) {
        return UserMapper.toInfoUserDto(findAndVerifyUserInRepository(userId));
    }

    @Override
    public InfoUserDto createUser(UserDto userDto) {
        if (userDto.getSubdivisionId() != null) {
            Subdivision subdivision = subdivisionRepository
                    .findById(userDto.getSubdivisionId())
                    .orElseThrow(() -> new DataNotFound(
                            String.format("User with id %d was not found in the database", userDto.getSubdivisionId())
                    ));
            return UserMapper.toInfoUserDto(userRepository.save(UserMapper.toUser(userDto, subdivision)));
        } else {
            return UserMapper.toInfoUserDto(userRepository.save(UserMapper.toUser(userDto)));
        }
    }

    @Override
    public InfoUserDto updateUser(UserDto userDto) {
        User user = findAndVerifyUserInRepository(userDto.getId());
        if (userDto.getSubdivisionId() != null) {
            return UserMapper.toInfoUserDto(userRepository.save(updateUserFromData(
                    UserMapper.toUser(userDto, getSubdivision(userDto.getSubdivisionId())), user)));
        } else {
            return UserMapper.toInfoUserDto(userRepository.save(updateUserFromData(
                    UserMapper.toUser(userDto), user)));
        }
    }

    private User findAndVerifyUserInRepository(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new DataNotFound(
                String.format("User with id %d was not found in the database", userId)));
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
        if (user.getSubdivision() != null) {
            userFromData.setSubdivision(user.getSubdivision());
        }
        return userFromData;
    }

    private Subdivision getSubdivision(Long subdivisionId) {
        return subdivisionRepository.findById(subdivisionId).orElseThrow(() -> new DataNotFound(
                        String.format("User with id %d was not found in the database", subdivisionId
                        )
                )
        );

    }
}