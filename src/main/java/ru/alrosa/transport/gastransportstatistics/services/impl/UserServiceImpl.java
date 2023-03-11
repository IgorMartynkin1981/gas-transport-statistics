package ru.alrosa.transport.gastransportstatistics.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.alrosa.transport.gastransportstatistics.dto.InfoUserDto;
import ru.alrosa.transport.gastransportstatistics.dto.UserAuthentication;
import ru.alrosa.transport.gastransportstatistics.dto.UserDto;
import ru.alrosa.transport.gastransportstatistics.dto.UserMapper;
import ru.alrosa.transport.gastransportstatistics.entity.Role;
import ru.alrosa.transport.gastransportstatistics.entity.Status;
import ru.alrosa.transport.gastransportstatistics.entity.Subdivision;
import ru.alrosa.transport.gastransportstatistics.entity.User;
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

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private SubdivisionRepository subdivisionRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           RoleRepository roleRepository,
                           SubdivisionRepository subdivisionRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.subdivisionRepository = subdivisionRepository;
        this.passwordEncoder = passwordEncoder;
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
    public User findByUsername(String username) {
        User result = userRepository.findByUsername(username);
        return result;
    }

    @Override
    public InfoUserDto createUser(UserAuthentication userAuthentication) {
        Role roleUser = roleRepository.findByName("ROLE_USER");
        List<Role> userRoles = new ArrayList<>();
        userRoles.add(roleUser);

        userAuthentication.setPassword(passwordEncoder.encode(userAuthentication.getPassword()));
        userAuthentication.setRoles(userRoles);
        userAuthentication.setCreated(LocalDateTime.now());
        userAuthentication.setUpdated(LocalDateTime.now());
        userAuthentication.setStatus(Status.ACTIVE);

        if (userAuthentication.getSubdivisionId() != null) {
            Subdivision subdivision = subdivisionRepository
                    .findById(userAuthentication.getSubdivisionId())
                    .orElseThrow(() -> new DataNotFound(
                            String.format("User with id %d was not found in the database", userAuthentication.getSubdivisionId())
                    ));
            return UserMapper.toInfoUserDto(userRepository.save(UserMapper.toUser(userAuthentication, subdivision)));
        } else {
            return UserMapper.toInfoUserDto(userRepository.save(UserMapper.toUser(userAuthentication)));
        }
    }

    @Override
    public InfoUserDto updateUser(UserDto userDto) {
        User user = findAndVerifyUserInRepository(userDto.getId());
        user.setUpdated(LocalDateTime.now());
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

    //TODO доделать
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

//        if (user.getLogin() != null) {
//            userFromData.setLogin(user.getLogin());
//        }

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