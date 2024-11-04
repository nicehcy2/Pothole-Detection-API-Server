package rootcode.roaddamagedetectionserver.management.controller.request;

import rootcode.roaddamagedetectionserver.management.Management;
import rootcode.roaddamagedetectionserver.management.RepairType;
import rootcode.roaddamagedetectionserver.pothole.entity.Pothole;

import java.time.LocalDateTime;

public record UpdatePotholeManagementRequestDTO(
        String managingInstitution,
        String repairType,
        String roadType,
        String manager,
        boolean isFixed,
        LocalDateTime repairStartAt,
        LocalDateTime repairEndAt
) {
    public Management toDomain(Pothole pothole) {
        return Management.builder()
                .pothole(pothole)
                .managingInstitution(managingInstitution)
                .repairType(RepairType.of(repairType))
                .manager(manager)
                .repairStartAt(repairStartAt)
                .repairEndAt(repairEndAt)
                .build();
    }
}
