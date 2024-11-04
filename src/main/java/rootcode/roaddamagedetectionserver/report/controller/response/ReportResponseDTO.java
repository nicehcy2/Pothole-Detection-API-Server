package rootcode.roaddamagedetectionserver.report.controller.response;

import lombok.Builder;
import rootcode.roaddamagedetectionserver.pothole.entity.Position;
import rootcode.roaddamagedetectionserver.report.entity.Report;

import java.time.LocalDateTime;

@Builder
public record ReportResponseDTO(
        long reportId,
        String imageUrl,
        String content,
        String location,
        boolean isChecked,
        Position position,
        LocalDateTime createAt,
        LocalDateTime updateAt
) {

    public static ReportResponseDTO of(Report report) {
        return ReportResponseDTO.builder()
                .reportId(report.getId())
                .content(report.getContent())
                .location(report.getLocation())
                .imageUrl(report.getImageUrl())
                .isChecked(report.isChecked())
                .position(report.getPosition())
                .createAt(report.getCreatedAt())
                .updateAt(report.getUpdatedAt())
                .build();
    }
}
