package ru.alrosa.transport.gastransportstatistics.plans.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.alrosa.transport.gastransportstatistics.exception.DataNotFound;
import ru.alrosa.transport.gastransportstatistics.exception.ValidationDataException;
import ru.alrosa.transport.gastransportstatistics.plans.dto.InfoPlanDto;
import ru.alrosa.transport.gastransportstatistics.plans.dto.PlanDto;
import ru.alrosa.transport.gastransportstatistics.plans.dto.PlanMapper;
import ru.alrosa.transport.gastransportstatistics.plans.model.Plan;
import ru.alrosa.transport.gastransportstatistics.plans.repositories.PlanRepository;
import ru.alrosa.transport.gastransportstatistics.serializationdeserialization.UtilClass;
import ru.alrosa.transport.gastransportstatistics.subdivisions.model.Subdivision;
import ru.alrosa.transport.gastransportstatistics.subdivisions.repositories.SubdivisionRepository;
import ru.alrosa.transport.gastransportstatistics.users.model.User;
import ru.alrosa.transport.gastransportstatistics.users.repositories.UserRepository;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class PlanServiceImpl implements PlanService {

    private final PlanRepository planRepository;
    private final UserRepository userRepository;
    private final SubdivisionRepository subdivisionRepository;


    @Autowired
    public PlanServiceImpl(PlanRepository planRepository,
                           UserRepository userRepository,
                           SubdivisionRepository subdivisionRepository) {
        this.planRepository = planRepository;
        this.userRepository = userRepository;
        this.subdivisionRepository = subdivisionRepository;
    }

    @Override
    public Collection<InfoPlanDto> getAllPlanDto(Long subdivisionId, String periodStart, String periodEnd) {
        if (subdivisionId != 0) {
            return planRepository.findAllByPeriodStartAfterAndPeriodEndBefore(subdivisionId, UtilClass.toLocalDateTime(periodStart),
                            UtilClass.toLocalDateTime(periodEnd))
                    .stream()
                    .map(PlanMapper::toInfoPlanDto)
                    .collect(Collectors.toList());
        } else {
            return planRepository.findAllByPeriodStartAfterAndPeriodEndBefore(UtilClass.toLocalDateTime(periodStart),
                            UtilClass.toLocalDateTime(periodEnd))
                    .stream()
                    .map(PlanMapper::toInfoPlanDto)
                    .collect(Collectors.toList());
        }
    }

    @Override
    public InfoPlanDto getPlanDtoById(Long planDtoId) {
        return PlanMapper.toInfoPlanDto(findAndVerifyPlanInRepository(planDtoId));
    }

    @Override
    public InfoPlanDto updatePlanDto(PlanDto planDto) {
        if (planDto.getId() == null || planDto.getId() <= 0) {
            throw new ValidationDataException("PlanId = null or Sub 0, it's wrong!");
        }
        return savePlan(planDto);
    }

    @Override
    public InfoPlanDto createPlanDto(PlanDto planDto) {
        return savePlan(planDto);
    }

    private InfoPlanDto savePlan(PlanDto planDto) {
        if (planDto.getUserId() == null || planDto.getUserId() <= 0 ||
                planDto.getSubdivisionId() == null || planDto.getSubdivisionId() <= 0) {
            throw new ValidationDataException("UserId or SubdivisionId = null, it's wrong!");
        }
        User user = userRepository.findById(planDto.getUserId()).orElseThrow(() -> new DataNotFound(
                String.format("User with id %d was not found in the database", planDto.getUserId())));
        Subdivision subdivision = subdivisionRepository
                .findById(planDto.getSubdivisionId())
                .orElseThrow(() -> new DataNotFound(
                        String.format("User with id %d was not found in the database", planDto.getSubdivisionId())
                ));
        return PlanMapper.toInfoPlanDto(planRepository.save(PlanMapper.toPlan(planDto, user, subdivision)));
    }

    @Override
    public void deletePlanDtoById(Long planDtoId) {
        planRepository.deleteById(planDtoId);
    }

    private Plan findAndVerifyPlanInRepository(Long planId) {
        return planRepository.findById(planId).orElseThrow(() -> new DataNotFound(
                String.format("Plan with id %d was not found in the database", planId)));
    }
}
