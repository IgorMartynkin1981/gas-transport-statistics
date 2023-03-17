package ru.alrosa.transport.gastransportstatistics.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.alrosa.transport.gastransportstatistics.entity.Subdivision;

@Repository
public interface SubdivisionRepository extends JpaRepository<Subdivision, Long> {
}
