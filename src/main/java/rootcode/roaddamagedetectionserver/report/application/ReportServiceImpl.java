package rootcode.roaddamagedetectionserver.report.application;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rootcode.roaddamagedetectionserver.common.ResponseCode;
import rootcode.roaddamagedetectionserver.common.util.AuthUtil;
import rootcode.roaddamagedetectionserver.pothole.entity.Position;
import rootcode.roaddamagedetectionserver.report.controller.request.ReportRequestDTO;
import rootcode.roaddamagedetectionserver.report.controller.response.ReportResponseDTO;
import rootcode.roaddamagedetectionserver.report.entity.Report;
import rootcode.roaddamagedetectionserver.report.exception.handler.ReportHandler;
import rootcode.roaddamagedetectionserver.report.repository.ReportJpaRepository;
import rootcode.roaddamagedetectionserver.user.User;
import rootcode.roaddamagedetectionserver.user.UserJpaRepository;
import rootcode.roaddamagedetectionserver.user.controller.exception.handler.UserHandler;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ReportServiceImpl implements ReportService {

    private final ReportJpaRepository reportRepository;
    private final UserJpaRepository userRepository;

    @Transactional
    @Override
    public Long addReport(ReportRequestDTO reportRequestDTO) {

        long userId = AuthUtil.getCurrentUserId();

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserHandler(ResponseCode.USER_NOT_FOUND));

        Report report = Report.builder()
                .reportRequestDTO(reportRequestDTO)
                .isChecked(false)
                .position(new Position(0, 0))
                .user(user)
                .build();

        Report savedReport = reportRepository.save(report);

        return savedReport.getId();
    }

    @Override
    public ReportResponseDTO getReportPothole(long reportId) {

        // User가 본인이 신고한 내역을 확인하는 메서드

        Report report = reportRepository.findById(reportId)
                .orElseThrow(() -> new ReportHandler(ResponseCode.REPORT_NOT_FOUND));

        return ReportResponseDTO.of(report);
    }

    @Override
    public List<ReportResponseDTO> getAllMyReportsPotholes() {

        long userId = AuthUtil.getCurrentUserId();

        User user = userRepository.findById(userId).
                orElseThrow(() -> new UserHandler(ResponseCode.USER_NOT_FOUND));

        return reportRepository.findAllByUser(user)
                .stream()
                .map(ReportResponseDTO::of)
                .toList();
    }

    @Override
    public List<ReportResponseDTO> getAllReportsPothole() {

        return reportRepository.findAll()
                .stream()
                .map(ReportResponseDTO::of)
                .toList();
    }

    @Transactional
    @Override
    public ReportResponseDTO updateReportPothole(long reportId) {
        validateAdminAccess();

        Report report = reportRepository.findById(reportId)
                .orElseThrow(() -> new ReportHandler(ResponseCode.REPORT_NOT_FOUND));

        report.setChecked(true);
        report.setPosition(getPosByGeocoding());

        reportRepository.save(report);

        return ReportResponseDTO.of(report);
    }

    @Override
    @Transactional
    public void deleteReportPothole(long reportId) {
        validateAdminAccess();

        Report report = reportRepository.findById(reportId)
                .orElseThrow(() -> new ReportHandler(ResponseCode.REPORT_NOT_FOUND));

        reportRepository.delete(report);
    }


    private Position getPosByGeocoding() {
        // TODO: 지도 API를 결정하고 구현하자
        return new Position(37, 128);
    }

    private void validateAdminAccess() {
        long userId = AuthUtil.getCurrentUserId();

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserHandler(ResponseCode.USER_NOT_FOUND));

        if (!user.isAdmin()) {
            throw new AccessDeniedException("관리자 권한이 필요합니다.");
        }
    }
}
