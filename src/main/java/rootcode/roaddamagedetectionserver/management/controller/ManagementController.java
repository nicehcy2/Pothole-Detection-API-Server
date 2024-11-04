package rootcode.roaddamagedetectionserver.management.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import rootcode.roaddamagedetectionserver.annotation.NeedLogin;
import rootcode.roaddamagedetectionserver.common.util.AuthUtil;
import rootcode.roaddamagedetectionserver.management.application.ManagementService;
import rootcode.roaddamagedetectionserver.management.controller.response.RepairPotholesResponseDTO;

import java.util.List;

@Tag(description = "관리자 관련 API", name = "Management")
@RequiredArgsConstructor
@RestController
public class ManagementController {
    private final ManagementService managementService;

    /**
     * 임시 코드인듯??
     *
     * @NeedLogin
     * @Operation(summary = "포트홀 보수 처리 정보 수정")
     * @PostMapping("/potholes/{potholeId}") public void updatePotholeManagementInfo(
     * @PathVariable long potholeId,
     * @RequestBody UpdatePotholeManagementRequestDTO request
     * ) {
     * managementServiceImpl.updatePotholeManagementInfo(potholeId, request);
     * }
     */

    @NeedLogin
    @Operation(summary = "포트홀 보수 처리 리스트 조회(관리자 권한 필요)")
    @GetMapping("/managements/potholes")
    public List<RepairPotholesResponseDTO> getRepairPotholeList(
            @RequestParam(defaultValue = "0") int page
    ) {
        long userId = AuthUtil.getCurrentUserId();

        return managementService.lookUpPotholes(userId, page);
    }

    @NeedLogin
    @Operation(summary = "특정 포트홀 보수 처리 체크")
    @PutMapping("/managements/potholes/{potholeId}")
    public long getRepairPotholeList(@PathVariable long potholeId) {
        long userId = AuthUtil.getCurrentUserId();

        return managementService.repairPothole(potholeId, userId);
    }
}
