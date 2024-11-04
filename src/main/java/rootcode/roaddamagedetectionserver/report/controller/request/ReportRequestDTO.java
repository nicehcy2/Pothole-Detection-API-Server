package rootcode.roaddamagedetectionserver.report.controller.request;

public record ReportRequestDTO(
        String imageUrl,
        String content,
        String location
) {
}
