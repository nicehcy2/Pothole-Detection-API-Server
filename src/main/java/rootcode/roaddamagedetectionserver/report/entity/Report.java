package rootcode.roaddamagedetectionserver.report.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rootcode.roaddamagedetectionserver.common.BaseEntity;
import rootcode.roaddamagedetectionserver.pothole.entity.Position;
import rootcode.roaddamagedetectionserver.report.controller.request.ReportRequestDTO;
import rootcode.roaddamagedetectionserver.user.User;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Report extends BaseEntity {

    @Embedded
    private Position position;

    @Column(name = "location", length = 100, nullable = false)
    private String location;

    @Column(name = "content", length = 50000, nullable = false)
    private String content;

    @Column(name = "image_url", length = 1000, nullable = false)
    private String imageUrl;

    @Column(name = "is_checked", nullable = false)
    private boolean isChecked;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private User user;

    public void check() {
        isChecked = true;
    }

    @Builder
    public Report(Position position, ReportRequestDTO reportRequestDTO, boolean isChecked, User user) {
        this.position = position;
        this.location = reportRequestDTO.location();
        this.content = reportRequestDTO.content();
        this.imageUrl = reportRequestDTO.imageUrl();
        this.isChecked = isChecked;
        this.user = user;
    }
}
