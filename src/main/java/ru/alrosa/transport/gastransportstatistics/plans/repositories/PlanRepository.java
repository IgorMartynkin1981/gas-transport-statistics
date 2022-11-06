package ru.alrosa.transport.gastransportstatistics.plans.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import ru.alrosa.transport.gastransportstatistics.plans.model.Plan;

@Component
public interface PlanRepository extends JpaRepository<Plan, Long> {
}
