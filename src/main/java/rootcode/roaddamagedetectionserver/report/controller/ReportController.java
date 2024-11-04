package rootcode.roaddamagedetectionserver.report.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import rootcode.roaddamagedetectionserver.annotation.NeedLogin;
import rootcode.roaddamagedetectionserver.report.application.ReportService;
import rootcode.roaddamagedetectionserver.report.controller.request.ReportRequestDTO;
import rootcode.roaddamagedetectionserver.report.controller.response.ReportResponseDTO;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/managements/reports")
public class ReportController {

    private final ReportService reportService;

    @NeedLogin
    @Operation(summary = "포트홀 신고 등록")
    @PostMapping
    public Long registerReport(@RequestBody ReportRequestDTO reportRequestDTO) {

        return reportService.addReport(reportRequestDTO);
    }

    @Operation(summary = "포트홀 신고 등록 확인")
    @GetMapping("/{reportId}")
    public ReportResponseDTO getReport(@PathVariable("reportId") long reportId) {

        return reportService.getReportPothole(reportId);
    }

    @NeedLogin
    @GetMapping("/my")
    public List<ReportResponseDTO> getAllMyReports() {

        return reportService.getAllMyReportsPotholes();
    }

    @NeedLogin
    @GetMapping
    public List<ReportResponseDTO> getAllReports() {

        return reportService.getAllReportsPothole();
    }

    @NeedLogin
    @PutMapping("/{reportId}")
    public ReportResponseDTO updateReport(@PathVariable("reportId") long reportId) {

        return reportService.updateReportPothole(reportId);
    }

    @NeedLogin
    @DeleteMapping("/{reportId}")
    public long deleteReport(@PathVariable("reportId") long reportId) {

        reportService.deleteReportPothole(reportId);
        return reportId;
    }
}
