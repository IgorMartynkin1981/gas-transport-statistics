package ru.alrosa.transport.gastransportstatistics.users.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.alrosa.transport.gastransportstatistics.exception.Create;
import ru.alrosa.transport.gastransportstatistics.users.dto.InfoUserDto;
import ru.alrosa.transport.gastransportstatistics.users.dto.UserDto;
import ru.alrosa.transport.gastransportstatistics.users.services.UserService;

import java.util.Collection;

@RestController
@RequestMapping(path = "/users")
public class UserController {
    private final UserService service;

    @Autowired
    public UserController(UserService service) {
        this.service = service;
    }


    @GetMapping
    public Collection<InfoUserDto> findAllUsers() {
        return service.getAllUsers();
    }

    @GetMapping("/{id}")
    public InfoUserDto getUserById(@PathVariable Long id) {
        return service.getUserById(id);
    }

    @PatchMapping("/{id}")
    public InfoUserDto updateUser(@RequestBody UserDto user) {
        System.out.println(user.toString());
        return service.updateUser(user);
    }

    @PostMapping
    public InfoUserDto createUser(@Validated({Create.class}) @RequestBody UserDto user) {
        return service.createUser(user);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUserById(@PathVariable Long id) {
        service.deleteUserById(id);
        return new ResponseEntity<>(String.format("User with ID: %s was deleted!", id), HttpStatus.OK);
    }
}
