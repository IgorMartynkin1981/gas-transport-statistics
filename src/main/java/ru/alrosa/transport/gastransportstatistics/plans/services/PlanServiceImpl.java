package ru.alrosa.transport.gastransportstatistics.plans.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.alrosa.transport.gastransportstatistics.plans.dto.PlanDto;
import ru.alrosa.transport.gastransportstatistics.plans.repositories.PlanRepository;

import java.util.Collection;

@Service
public class PlanServiceImpl implements PlanService{

    private final PlanRepository planRepository;

    @Autowired
    public PlanServiceImpl(PlanRepository planRepository) {
        this.planRepository = planRepository;
    }

    @Override
    public Collection<PlanDto> getAllPlanDto() {
        return null;
    }

    @Override
    public PlanDto getPlanDtoById(Long planDtoId) {
        return null;
    }

    @Override
    public PlanDto updatePlanDto(PlanDto planDto) {
        return null;
    }

    @Override
    public PlanDto createPlanDto(PlanDto planDto) {
        return null;
    }

    @Override
    public void deletePlanDtoById(Long planDtoId) {

    }
}
