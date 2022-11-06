package ru.alrosa.transport.gastransportstatistics.users.dto;


import ru.alrosa.transport.gastransportstatistics.users.model.User;

public class UserMapper {
    public static UserDto toUserDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getFirstName());
        userDto.setEmail(user.getEmail());
        userDto.setLogin(user.getLogin());
        userDto.setSubdivisionId(user.getSubdivisionId());

        return userDto;
    }

    public static User toUser(UserDto userDto) {
        User user = new User();
        if (userDto.getId() != null) {
            user.setId(userDto.getId());
        }
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user.setLogin(userDto.getLogin());
        user.setSubdivisionId(userDto.getSubdivisionId());

        return user;
    }
}
