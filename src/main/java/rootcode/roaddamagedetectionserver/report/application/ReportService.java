package rootcode.roaddamagedetectionserver.report.application;

import rootcode.roaddamagedetectionserver.report.controller.request.ReportRequestDTO;
import rootcode.roaddamagedetectionserver.report.controller.response.ReportResponseDTO;

import java.util.List;

public interface ReportService {
    Long addReport(ReportRequestDTO request);

    ReportResponseDTO getReportPothole(long reportId);

    List<ReportResponseDTO> getAllMyReportsPotholes();

    List<ReportResponseDTO> getAllReportsPothole();

    ReportResponseDTO updateReportPothole(long reportId);

    void deleteReportPothole(long reportId);
}
