package ru.alrosa.transport.gastransportstatistics.users;

import org.junit.jupiter.api.Test;
import ru.alrosa.transport.gastransportstatistics.mockdata.MockData;
import ru.alrosa.transport.gastransportstatistics.users.model.User;

import java.io.IOException;
import java.util.List;

public class UserServiceTest {

    @Test
    public void sortingSteamOfElements() throws IOException {
        List<User> people = MockData.getPeople();
        List<String> sorted = people.stream()
                .map(User::getFirstName)
                .sorted()
                .toList();
        people.forEach(System.out::println);
        sorted.forEach(System.out::println);
        //sorted.forEach(System.out::println);
    }

    @Test
    public void createUserTest() throws IOException {

    }
}
