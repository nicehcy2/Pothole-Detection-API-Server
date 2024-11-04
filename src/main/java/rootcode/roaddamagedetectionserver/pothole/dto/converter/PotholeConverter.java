package rootcode.roaddamagedetectionserver.pothole.dto.converter;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import rootcode.roaddamagedetectionserver.pothole.controller.response.PotholeDetail;
import rootcode.roaddamagedetectionserver.pothole.entity.Pothole;
import rootcode.roaddamagedetectionserver.region.service.RegionNameServiceImpl;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PotholeConverter {

    public static PotholeDetail convertToPotholeDto(Pothole pothole, String imageUrl) {

        String regionName = pothole.getAddress();
        String sidoRegion = regionName == null ? null : RegionNameServiceImpl.extractRegionFirstPart(regionName);
        String sigunguRegion = regionName == null ? null : RegionNameServiceImpl.extractRegionSecondPart(regionName);

        // 위도, 경도 부분 수정 필요!
        return PotholeDetail.builder()
                .potholeId(pothole.getId())
                .latitude(pothole.getLatitude())
                .longitude(pothole.getLongitude())
                .regionName(regionName)
                .sido(sidoRegion)
                .sigungu(sigunguRegion)
                .imageUrl(imageUrl)
                .isFixed(pothole.isFixed())
                .isAiVerified(pothole.isAiVerified())
                .createdAt(pothole.getCreatedAt())
                .updateAt(pothole.getUpdatedAt())
                .build();
    }
}
