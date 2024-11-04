package rootcode.roaddamagedetectionserver.management.controller.response;

import lombok.Builder;
import rootcode.roaddamagedetectionserver.image.Image;
import rootcode.roaddamagedetectionserver.pothole.entity.Position;
import rootcode.roaddamagedetectionserver.pothole.entity.Pothole;

import java.time.LocalDateTime;

@Builder
public record RepairPotholesResponseDTO(
        long potholeId,
        Position position,
        boolean isFixed,
        String address,
        String sido,
        String sigungu,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        String imageUrl
) {
    public static RepairPotholesResponseDTO of(Pothole pothole, Image image) {

        String regionName = pothole.getRegion().toString();

        String[] parts = regionName.split("_");

        String sidoName = null;
        String sigunguName = null;

        if (regionName != "ALL") {
            sidoName = parts[0].toLowerCase();
            sigunguName = parts[1].toLowerCase();
        }

        return RepairPotholesResponseDTO.builder()
                .potholeId(pothole.getId())
                .position(pothole.getPosition())
                .isFixed(pothole.isFixed())
                .address(pothole.getAddress())
                .sido(sidoName)
                .sigungu(sigunguName)
                .createdAt(pothole.getCreatedAt())
                .updatedAt(pothole.getUpdatedAt())
                .imageUrl(image != null ? image.getUrl() : null)
                .build();
    }
}
