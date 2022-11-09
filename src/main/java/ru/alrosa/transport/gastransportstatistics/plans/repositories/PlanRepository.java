package ru.alrosa.transport.gastransportstatistics.plans.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;
import ru.alrosa.transport.gastransportstatistics.plans.model.Plan;

import java.time.LocalDate;

@Component
public interface PlanRepository extends JpaRepository<Plan, Long> {

    @Modifying
    @Query("SELECT b FROM Plan AS b " +
            "WHERE b.subdivision.id = ?1 AND b.periodStart = ?2 AND b.periodEnd = ?3")
    Plan findPlanBySubdivisionIdFromPeriod(Long subdivisionId, LocalDate periodStart, LocalDate periodEnd);

    /*
    @Modifying
    @Query("SELECT subdivision_id, SUM(PLAN_CONSUMPTION_GAS), SUM(PLAN_CONSUMPTION_DT), " +
            "SUM(PLAN_DISTANCE_GAS), SUM(PLAN_DISTANCE_DT) FROM PLAN_GAS_CONSUMPTION " +
            "WHERE SUBDIVISION_ID = 1 AND PERIOD_START >= '2022-01-01' AND PERIOD_END <= '2023-01-01' " +
            "GROUP BY subdivision_id")
    Plan wert();
    */
}
