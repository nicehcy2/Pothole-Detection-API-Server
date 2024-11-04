package rootcode.roaddamagedetectionserver.management;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import rootcode.roaddamagedetectionserver.common.BaseEntity;
import rootcode.roaddamagedetectionserver.pothole.entity.Pothole;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Management extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pothole_id", nullable = false)
    private Pothole pothole;

    @Column(name = "managing_institution")
    private String managingInstitution;

    @Enumerated(EnumType.STRING)
    @Column(name = "repair_type", nullable = false)
    private RepairType repairType;

    @Column(name = "manager")
    private String manager;

    @Column(name = "repair_start_at")
    private LocalDateTime repairStartAt;

    @Column(name = "repair_end_at")
    private LocalDateTime repairEndAt;
}
