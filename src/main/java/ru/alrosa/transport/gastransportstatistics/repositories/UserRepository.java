package ru.alrosa.transport.gastransportstatistics.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import ru.alrosa.transport.gastransportstatistics.entity.User;

@Component
public interface UserRepository extends JpaRepository<User, Long> {


}
