package ru.alrosa.transport.gastransportstatistics.subdivisions.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import ru.alrosa.transport.gastransportstatistics.subdivisions.model.Subdivision;

@Component
public interface SubdivisionRepository extends JpaRepository<Subdivision, Long> {
}
