package rootcode.roaddamagedetectionserver.pothole.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import rootcode.roaddamagedetectionserver.pothole.application.PotholeService;
import rootcode.roaddamagedetectionserver.pothole.controller.request.AddPotholeRequestDTO;
import rootcode.roaddamagedetectionserver.pothole.controller.request.SecondVerificationRequestDTO;
import rootcode.roaddamagedetectionserver.pothole.controller.response.PotholeDetailResponseDTO;
import rootcode.roaddamagedetectionserver.pothole.controller.response.PotholeStatusResponseDTO;

@Tag(description = "포트홀 관련 API", name = "Pothole")
@RequiredArgsConstructor
@RestController
public class PotholeController {

    private final PotholeService potholeService;

    //@NeedLogin
    @Operation(summary = "포트홀 정보 추가")
    @PostMapping("/potholes")
    public void addPothole(
            @RequestBody AddPotholeRequestDTO request
    ) {
        potholeService.addPothole(request);
    }

    @Operation(summary = "포트홀 정보 조회")
    @GetMapping("/potholes/{potholeId}")
    public PotholeDetailResponseDTO getPothole(
            @PathVariable("potholeId") long potholeId
    ) {
        return potholeService.getPothole(potholeId);
    }

    @Operation(summary = "포트홀 전체 현황 건수")
    @GetMapping("/potholes/status")
    public PotholeStatusResponseDTO getAllPotholeStatus() {
        return potholeService.getStatus();
    }

    @Operation(summary = "포트홀 전체 조회")
    @GetMapping("/potholes")
    public PotholeListResponseDTO getAllPotholes(
            @RequestParam(value = "fixed", required = false) Boolean fixed, // null이면 전체, true면 수리완료, false면 미수리
            @RequestParam(value = "page", defaultValue = "0") int page
    ) {
        return potholeService.getAll(fixed, page);
    }

    @Operation(summary = "2차 검증된 포트홀 전체 조회")
    @GetMapping("/potholes/valid")
    public PotholeListResponseDTO getAllValidPotholes(
            @RequestParam(value = "fixed", required = false) Boolean fixed, // null이면 전체, true면 수리완료, false면 미수리
            @RequestParam(value = "page", defaultValue = "0") int page
    ) {
        return potholeService.getValidAll(fixed, page);
    }

    @Operation(summary = "포트홀 월별 발생 건수")
    @GetMapping("/potholes/statistics/monthly")
    public PotholeMonthlyStatisticsResponseDTO getMonthlyStatistics() {
        return potholeService.getMonthlyStatistics();
    }

    @Transactional
    @Operation(summary = "2차 검증 결과 처리")
    @PostMapping("/potholes/second-verification")
    public void secondVerification(
            @RequestBody SecondVerificationRequestDTO request
    ) {
        potholeService.secondVerification(
                request.validPotholeIds(),
                request.invalidPotholeIds(),
                request.potholeUrl()
        );
    }

    @Operation(summary = "해당 좌표에 포트홀이 있는지 확인")
    @GetMapping("/potholes/geohash")
    public boolean checkPotholeExists(
            @RequestParam(value = "geohash") String geohash) {

        return potholeService.checkPotholeExists(geohash);
    }
}
