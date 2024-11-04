package rootcode.roaddamagedetectionserver.pothole.controller.request;

public record AddPotholeRequestDTO(
        double latitude,
        double longitude,
        String imageUrl,
        String geohash
) {
}