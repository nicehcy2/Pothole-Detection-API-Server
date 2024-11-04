package rootcode.roaddamagedetectionserver.pothole.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rootcode.roaddamagedetectionserver.common.BaseEntity;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Pothole extends BaseEntity {
    @Column(name = "is_fixed", nullable = false)
    private boolean isFixed;

    // 2차 검증을 했는지 여부
    @Column(name = "is_ai_verified", nullable = false)
    private boolean isAiVerified;

    @Embedded
    private Position position;

    @Enumerated(EnumType.STRING)
    @Column(name = "region")
    private PotholeRegion region;

    @Column(name = "found_at", nullable = false)
    private LocalDateTime foundAt;

    @Column(name = "address")
    private String address;

    @Column(name = "geohash", length = 12)
    private String geohash;

    @Enumerated(EnumType.STRING)
    @Column(name = "road_type")
    private RoadType roadType;

    // 2차 검증을 통해 포트홀로 인정 받았는지 여부
    @Column(name = "is_valid", nullable = false)
    private boolean isValid;

    public double getLatitude() {
        return position.getLatitude();
    }

    public double getLongitude() {
        return position.getLongitude();
    }

    public void applyManagementInfo(boolean isFixed, RoadType roadType) {
        this.isFixed = isFixed;
        this.roadType = roadType;
    }
}
