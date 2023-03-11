package ru.alrosa.transport.gastransportstatistics.dto;


import ru.alrosa.transport.gastransportstatistics.entity.Subdivision;
import ru.alrosa.transport.gastransportstatistics.entity.User;

public class UserMapper {
    public static InfoUserDto toInfoUserDto(User user) {
        InfoUserDto infoUserDto = new InfoUserDto();
        infoUserDto.setId(user.getId());
        infoUserDto.setFirstName(user.getFirstName());
        infoUserDto.setLastName(user.getLastName());
        infoUserDto.setEmail(user.getEmail());
        infoUserDto.setSubdivision(user.getSubdivision());

        return infoUserDto;
    }

    public static User toUser(UserDto userDto, Subdivision subdivision) {
        User user = new User();
        if (userDto.getId() != null) {
            user.setId(userDto.getId());
        }
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user.setUsername(userDto.getUserName());
        user.setPassword(userDto.getPassword());
        user.setRoles(userDto.getRoles());
        user.setStatus(userDto.getStatus());
        user.setSubdivision(subdivision);

        return user;
    }

    public static User toUser(UserDto userDto) {
        User user = new User();
        if (userDto.getId() != null) {
            user.setId(userDto.getId());
        }
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user.setUsername(userDto.getUserName());
        user.setPassword(userDto.getPassword());
        user.setRoles(userDto.getRoles());
        user.setStatus(userDto.getStatus());

        return user;
    }
}
