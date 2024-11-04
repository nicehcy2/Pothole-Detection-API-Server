package rootcode.roaddamagedetectionserver.alarm.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import rootcode.roaddamagedetectionserver.common.BaseEntity;
import rootcode.roaddamagedetectionserver.user.User;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Alarm extends BaseEntity {

    @Column(name = "title", length = 30, nullable = false)
    private String title;

    @Column(name = "content", length = 1000, nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}
