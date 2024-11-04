package rootcode.roaddamagedetectionserver.image;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import rootcode.roaddamagedetectionserver.common.BaseEntity;
import rootcode.roaddamagedetectionserver.pothole.entity.Pothole;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Image extends BaseEntity {
    @Column(name = "image_url", nullable = false)
    private String url;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pothole_id", nullable = false)
    private Pothole pothole;
}
