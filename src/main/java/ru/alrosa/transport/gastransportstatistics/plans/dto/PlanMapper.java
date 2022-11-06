package ru.alrosa.transport.gastransportstatistics.plans.dto;

import ru.alrosa.transport.gastransportstatistics.plans.model.Plan;

public class PlanMapper {

    public static PlanDto toPlanDto(Plan plan) {
        PlanDto planDto = new PlanDto();
        planDto.setId(plan.getId());
        planDto.setUserId(plan.getUserId());
        planDto.setSubdivisionId(plan.getSubdivisionId());
        planDto.setCreationTime(plan.getCreationTime());
        planDto.setPeriodStart(plan.getPeriodStart());
        planDto.setPeriodEnd(plan.getPeriodEnd());
        planDto.setPlanConsumptionGas(plan.getPlanConsumptionGas());
        planDto.setPlanDistanceGas(plan.getPlanDistanceGas());
        planDto.setPlanConsumptionDt(plan.getPlanConsumptionDt());
        planDto.setPlanDistanceDt(plan.getPlanDistanceDt());

        return planDto;
    }

    public static Plan toPlan(PlanDto planDto) {
        Plan plan = new Plan();
        if (planDto.getId() != null) {
            plan.setId(planDto.getId());
        }
        if (planDto.getUserId() != null) {
            plan.setUserId(planDto.getUserId());
        }
        plan.setSubdivisionId(planDto.getSubdivisionId());
        plan.setCreationTime(planDto.getCreationTime());
        plan.setPeriodStart(planDto.getPeriodStart());
        plan.setPeriodEnd(planDto.getPeriodEnd());
        plan.setPlanConsumptionGas(planDto.getPlanConsumptionGas());
        plan.setPlanDistanceGas(planDto.getPlanDistanceGas());
        plan.setPlanConsumptionDt(planDto.getPlanConsumptionDt());
        plan.setPlanDistanceDt(planDto.getPlanDistanceDt());

        return plan;
    }
}
