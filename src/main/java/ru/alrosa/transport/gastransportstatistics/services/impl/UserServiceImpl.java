package ru.alrosa.transport.gastransportstatistics.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.alrosa.transport.gastransportstatistics.dto.InfoUserDto;
import ru.alrosa.transport.gastransportstatistics.dto.UserAuthentication;
import ru.alrosa.transport.gastransportstatistics.dto.UserMapper;
import ru.alrosa.transport.gastransportstatistics.entity.Role;
import ru.alrosa.transport.gastransportstatistics.entity.Subdivision;
import ru.alrosa.transport.gastransportstatistics.entity.User;
import ru.alrosa.transport.gastransportstatistics.entity.enums.Status;
import ru.alrosa.transport.gastransportstatistics.exception.DataNotFound;
import ru.alrosa.transport.gastransportstatistics.repositories.RoleRepository;
import ru.alrosa.transport.gastransportstatistics.repositories.SubdivisionRepository;
import ru.alrosa.transport.gastransportstatistics.repositories.UserRepository;
import ru.alrosa.transport.gastransportstatistics.services.UserService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of {@link UserService} interface.
 * Wrapper for {@link UserRepository} + business logic.
 *
 * @author Igor Martynkin
 * @version 1.0
 */

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final SubdivisionRepository subdivisionRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           SubdivisionRepository subdivisionRepository) {
        this.userRepository = userRepository;
        this.subdivisionRepository = subdivisionRepository;
    }

    @Override
    public Collection<InfoUserDto> getAllUsers() {
        return userRepository
                .findAll()
                .stream()
                .map(UserMapper::toInfoUserDto)
                .collect(Collectors.toList());
    }

    @Override
    public InfoUserDto getUserById(Long userId) {
        return UserMapper.toInfoUserDto(findAndVerifyUserInRepository(userId));
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public InfoUserDto createUser(UserAuthentication userAuthentication) {
        userAuthentication.setCreated(LocalDateTime.now());
        userAuthentication.setUpdated(LocalDateTime.now());
        userAuthentication.setStatus(Status.ACTIVE);

        if (userAuthentication.getSubdivisionId() != null) {
            Subdivision subdivision = subdivisionRepository
                    .findById(userAuthentication.getSubdivisionId())
                    .orElseThrow(() -> new DataNotFound(
                            String.format("User with id %d was not found in the database",
                                    userAuthentication.getSubdivisionId())
                    ));
            return UserMapper.toInfoUserDto(userRepository.save(UserMapper.toUser(userAuthentication, subdivision)));
        } else {
            return UserMapper.toInfoUserDto(userRepository.save(UserMapper.toUser(userAuthentication)));
        }
    }

    @Override
    public InfoUserDto updateUser(UserAuthentication userAuthentication) {
        User user = findAndVerifyUserInRepository(userAuthentication.getId());
        user.setUpdated(LocalDateTime.now());

        if (userAuthentication.getSubdivisionId() != null) {
            return UserMapper.toInfoUserDto(userRepository.
                    save(updateUserFromData(
                            UserMapper.toUser(
                                    userAuthentication,
                                    getSubdivision(userAuthentication.getSubdivisionId())),
                            user)
                    )
            );
        } else {
            return UserMapper.toInfoUserDto(userRepository.
                    save(updateUserFromData(
                            UserMapper.toUser(userAuthentication),
                            user)
                    )
            );
        }
    }

    @Override
    public void deactivationUser(Long userId) {
        User user = findAndVerifyUserInRepository(userId);

        user.setStatus(Status.DISABLE);
        user.setUpdated(LocalDateTime.now());

        userRepository.save(user);
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

        if (user.getUsername() != null) userFromData.setUsername(user.getUsername());
        if (user.getFirstName() != null) userFromData.setFirstName(user.getFirstName());
        if (user.getLastName() != null) userFromData.setLastName(user.getLastName());
        if (user.getEmail() != null) userFromData.setEmail(user.getEmail());
        if (user.getSubdivision() != null) userFromData.setSubdivision(user.getSubdivision());
        if (user.getStatus() != null) userFromData.setStatus(user.getStatus());

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