package rootcode.roaddamagedetectionserver.pothole.controller.response;

public record PotholeStatusResponseDTO(
        long totalPotholes,
        long fixedPotholes,
        long unfixedPotholes
) {
}
