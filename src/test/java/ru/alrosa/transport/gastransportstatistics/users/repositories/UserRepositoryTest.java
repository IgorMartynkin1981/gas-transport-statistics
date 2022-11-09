package ru.alrosa.transport.gastransportstatistics.users.repositories;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import ru.alrosa.transport.gastransportstatistics.GastransportstatisticsApplication;
import ru.alrosa.transport.gastransportstatistics.users.model.User;
import ru.alrosa.transport.gastransportstatistics.users.services.UserServiceImpl;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Transactional
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@SpringJUnitConfig({GastransportstatisticsApplication.class, UserServiceImpl.class})
class UserRepositoryTest {

    private EntityManager entityManager;
    private UserRepository userRepository;

    @BeforeEach
    void beforeEach() {
        userRepository = mock(UserRepository.class);
    }

    @Test
    void createUserTest() {
        User user = new User();
        user.setFirstName("Igor");
        user.setLastName("Martynkin");
        user.setEmail("MartynkinIA@alrosa.ru");
        user.setLogin("MartynkinIA");

        when(userRepository.save(user)).thenReturn(user);

        TypedQuery<User> query = entityManager.createQuery("Select u from User u where u.id = :id", User.class);

                User queryUser = query
                .setParameter("id", 1L)
                .getSingleResult();
        assertEquals(user, queryUser);
    }

    @Test
    void updateUserTest() {
        User user = new User();
        user.setFirstName("Igor");
        user.setLastName("Martynkin");
        user.setEmail("MartynkinIA@alrosa.ru");
        user.setLogin("MartynkinIA");

        userRepository.save(user);

        {
            TypedQuery<User> query = entityManager.createQuery("Select u from User u where u.id = :id", User.class);
            User queryUser = query
                    .setParameter("id", 1L)
                    .getSingleResult();

            queryUser.setFirstName("Garik");
            userRepository.save(queryUser);
        }

        TypedQuery<User> query = entityManager.createQuery("Select u from User u where u.id = :id", User.class);
        User queryUser = query
                .setParameter("id", 1L)
                .getSingleResult();

        assertEquals("Garik", queryUser.getFirstName());
        assertEquals("Martynkin", queryUser.getLastName());
        assertEquals("MartynkinIA@alrosa.ru", queryUser.getEmail());
    }

    @Test
    void findUserByIdTest() {
        User user = new User();
        user.setFirstName("Igor");
        user.setLastName("Martynkin");
        user.setEmail("MartynkinIA@alrosa.ru");
        user.setLogin("MartynkinIA");

        User user2 = new User();
        user2.setFirstName("Igor");
        user2.setLastName("Martynkin");
        user2.setEmail("MartynkinIA1@alrosa.ru");
        user2.setLogin("MartynkinIA");

        userRepository.save(user);
        userRepository.save(user2);

        TypedQuery<User> query = entityManager.createQuery("Select u from User u where u.id = :id", User.class);
        User queryUser = query
                .setParameter("id", 1L)
                .getSingleResult();
        User queryUser2 = query
                .setParameter("id", 2L)
                .getSingleResult();
        assertEquals(user, queryUser);
        assertEquals(user2, queryUser2);
    }



}