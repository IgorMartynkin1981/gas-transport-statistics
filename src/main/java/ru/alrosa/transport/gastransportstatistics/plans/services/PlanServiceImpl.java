package ru.alrosa.transport.gastransportstatistics.plans.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.alrosa.transport.gastransportstatistics.exception.DataNotFound;
import ru.alrosa.transport.gastransportstatistics.plans.dto.InfoPlanDto;
import ru.alrosa.transport.gastransportstatistics.plans.dto.PlanDto;
import ru.alrosa.transport.gastransportstatistics.plans.dto.PlanMapper;
import ru.alrosa.transport.gastransportstatistics.plans.model.Plan;
import ru.alrosa.transport.gastransportstatistics.plans.repositories.PlanRepository;
import ru.alrosa.transport.gastransportstatistics.users.model.User;
import ru.alrosa.transport.gastransportstatistics.users.repositories.UserRepository;

import java.util.Collection;

@Service
public class PlanServiceImpl implements PlanService {

    private final PlanRepository planRepository;
    private final UserRepository userRepository;


    @Autowired
    public PlanServiceImpl(PlanRepository planRepository, UserRepository userRepository) {
        this.planRepository = planRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Collection<InfoPlanDto> getAllPlanDto() {
        return planRepository.findAll().stream().map(PlanMapper::toInfoPlanDto).toList();
    }

    @Override
    public InfoPlanDto getPlanDtoById(Long planDtoId) {
        return PlanMapper.toInfoPlanDto(findAndVerifyPlanInRepository(planDtoId));
    }

    @Override
    public InfoPlanDto updatePlanDto(PlanDto planDto) {
        return null;
    }

    @Override
    public InfoPlanDto createPlanDto(PlanDto planDto) {
        User user = userRepository.findById(planDto.getUserId()).orElseThrow(() -> new DataNotFound(
                String.format("User with id %d was not found in the database", planDto.getUserId())));

        return PlanMapper.toInfoPlanDto(planRepository.save(PlanMapper.toPlan(planDto, user)));
    }

    @Override
    public void deletePlanDtoById(Long planDtoId) {

    }

    private Plan findAndVerifyPlanInRepository(Long planId) {
        return planRepository.findById(planId).orElseThrow(() -> new DataNotFound(
                String.format("Plan with id %d was not found in the database", planId)));
    }
}