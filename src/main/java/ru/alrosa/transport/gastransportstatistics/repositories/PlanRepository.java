package ru.alrosa.transport.gastransportstatistics.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.alrosa.transport.gastransportstatistics.entity.Plan;

import java.time.LocalDate;
import java.util.Collection;

@Repository
public interface PlanRepository extends JpaRepository<Plan, Long> {

    @Modifying
    @Query("SELECT b FROM Plan AS b " +
            "WHERE b.periodStart >= ?1 AND b.periodEnd <= ?2")
    Collection<Plan> findAllByPeriodStartAfterAndPeriodEndBefore(LocalDate periodStart, LocalDate periodEnd);

    @Modifying
    @Query("SELECT b FROM Plan AS b " +
            "WHERE b.subdivision.id = ?1 AND b.periodStart >= ?2 AND b.periodEnd <= ?3")
    Collection<Plan> findAllByPeriodStartAfterAndPeriodEndBefore(Long subdivisionId,
                                                                 LocalDate periodStart,
                                                                 LocalDate periodEnd);
}
