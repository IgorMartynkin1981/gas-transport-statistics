package ru.alrosa.transport.gastransportstatistics.users.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import ru.alrosa.transport.gastransportstatistics.users.model.User;
import ru.alrosa.transport.gastransportstatistics.users.services.UserServiceImpl;

@Component
public interface UserRepository extends JpaRepository<User, Long> {


}
