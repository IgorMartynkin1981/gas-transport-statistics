package ru.alrosa.transport.gastransportstatistics.plans.dto;

import ru.alrosa.transport.gastransportstatistics.plans.model.Plan;
import ru.alrosa.transport.gastransportstatistics.users.model.User;

import java.time.LocalDate;

public class PlanMapper {

    public static InfoPlanDto toInfoPlanDto(Plan plan) {
        InfoPlanDto infoPlanDto = new InfoPlanDto();
        infoPlanDto.setId(plan.getId());
        infoPlanDto.setUser(plan.getUser());
        infoPlanDto.setSubdivision(plan.getSubdivision());
        infoPlanDto.setCreationTime(plan.getCreationTime());
        infoPlanDto.setPeriodStart(plan.getPeriodStart());
        infoPlanDto.setPeriodEnd(plan.getPeriodEnd());
        infoPlanDto.setPlanConsumptionGas(plan.getPlanConsumptionGas());
        infoPlanDto.setPlanDistanceGas(plan.getPlanDistanceGas());
        infoPlanDto.setPlanConsumptionDt(plan.getPlanConsumptionDt());
        infoPlanDto.setPlanDistanceDt(plan.getPlanDistanceDt());

        return infoPlanDto;
    }

    public static Plan toPlan(PlanDto planDto, User user) {
        Plan plan = new Plan();
        if (planDto.getId() != null) {
            plan.setId(planDto.getId());
        }
        plan.setUser(user);
        plan.setSubdivision(user.getSubdivision());
        if (planDto.getCreationTime() != null) {
            plan.setCreationTime(planDto.getCreationTime());
        } else {
            plan.setCreationTime(LocalDate.now());
        }
        plan.setPeriodStart(planDto.getPeriodStart());
        plan.setPeriodEnd(planDto.getPeriodEnd());
        plan.setPlanConsumptionGas(planDto.getPlanConsumptionGas());
        plan.setPlanDistanceGas(planDto.getPlanDistanceGas());
        plan.setPlanConsumptionDt(planDto.getPlanConsumptionDt());
        plan.setPlanDistanceDt(planDto.getPlanDistanceDt());

        return plan;
    }
}
