package ru.alrosa.transport.gastransportstatistics.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.alrosa.transport.gastransportstatistics.dto.UserAuthentication;
import ru.alrosa.transport.gastransportstatistics.services.UserService;

/**
 * REST controller for authentication requests (login, logout, register, etc.)
 *
 * @author Igor Martynkin
 * @version 1.0
 */

@RestController
public class AuthenticationController {

    private final UserService userService;

    @Autowired
    public AuthenticationController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/user_console/signup")
    public ResponseEntity<?> registrationUser(@RequestBody UserAuthentication user) {
        return ResponseEntity.ok(userService.createUser(user));
    }

    @PatchMapping("/user_console/update")
    public ResponseEntity<?> updateUser(@RequestBody UserAuthentication user) {
        return ResponseEntity.ok(userService.updateUser(user));
    }

    @DeleteMapping("/admin_console/delete/{id}")
    public ResponseEntity<String> deleteUserById(@PathVariable Long id) {
        userService.deleteUserById(id);
        return new ResponseEntity<>(String.format("User with ID: %s was deleted!", id), HttpStatus.OK);
    }
}
