package rootcode.roaddamagedetectionserver.pothole.controller.response;

import lombok.Builder;

import java.time.LocalDateTime;


@Builder
public record PotholeDetail(
        long potholeId,
        double latitude,
        double longitude,
        String regionName,
        String sido,
        String sigungu,
        String imageUrl,
        boolean isFixed,
        boolean isValid,
        boolean isAiVerified,
        LocalDateTime createdAt,
        LocalDateTime updateAt
) {
}
