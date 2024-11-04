package rootcode.roaddamagedetectionserver.management.application;

import rootcode.roaddamagedetectionserver.management.controller.request.UpdatePotholeManagementRequestDTO;
import rootcode.roaddamagedetectionserver.management.controller.response.RepairPotholesResponseDTO;

import java.util.List;

public interface ManagementService {

    void updatePotholeManagementInfo(long potholeId, UpdatePotholeManagementRequestDTO request);

    List<RepairPotholesResponseDTO> lookUpPotholes(long userId, int page);

    long repairPothole(long potholeId, long userId);
}
