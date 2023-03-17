package ru.alrosa.transport.gastransportstatistics.dto;


import ru.alrosa.transport.gastransportstatistics.entity.Subdivision;
import ru.alrosa.transport.gastransportstatistics.entity.User;

public class UserMapper {
    public static InfoUserDto toInfoUserDto(User user) {
        InfoUserDto infoUserDto = new InfoUserDto();
        infoUserDto.setId(user.getId());
        infoUserDto.setUsername((user.getUsername()));
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

        return user;
    }

    //This method only created new user
    public static User toUser(UserAuthentication userAuthentication, Subdivision subdivision) {
        User user = new User();

        if (userAuthentication.getId() != null) {
            user.setId(userAuthentication.getId());
        }

        user.setUsername(userAuthentication.getUsername());
        user.setFirstName(userAuthentication.getFirstName());
        user.setLastName(userAuthentication.getLastName());
        user.setEmail(userAuthentication.getEmail());
        user.setPassword(userAuthentication.getPassword());
        user.setRoles(userAuthentication.getRoles());
        user.setStatus(userAuthentication.getStatus());
        user.setSubdivision(subdivision);
        user.setCreated(userAuthentication.getCreated());
        user.setUpdated(userAuthentication.getUpdated());

        return user;
    }

    public static User toUser(UserAuthentication userAuthentication) {
        User user = new User();

        if (userAuthentication.getId() != null) {
            user.setId(userAuthentication.getId());
        }

        user.setUsername(userAuthentication.getUsername());
        user.setFirstName(userAuthentication.getFirstName());
        user.setLastName(userAuthentication.getLastName());
        user.setEmail(userAuthentication.getEmail());
        user.setPassword(userAuthentication.getPassword());
        user.setRoles(userAuthentication.getRoles());
        user.setStatus(userAuthentication.getStatus());
        user.setCreated(userAuthentication.getCreated());
        user.setUpdated(userAuthentication.getUpdated());

        return user;
    }
}
