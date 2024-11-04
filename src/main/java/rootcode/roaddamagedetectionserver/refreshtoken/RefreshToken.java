package rootcode.roaddamagedetectionserver.refreshtoken;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import rootcode.roaddamagedetectionserver.common.BaseEntity;
import rootcode.roaddamagedetectionserver.jwt.TokenInfo;

import java.time.LocalDateTime;

import static rootcode.roaddamagedetectionserver.common.util.DateUtil.toLocalDateTime;

@Getter
@NoArgsConstructor
@Entity
public class RefreshToken extends BaseEntity {
    @Column(name = "user_id", nullable = false, updatable = false)
    private long userId;

    @Column(name = "token", nullable = false, updatable = false)
    private String token;

    @Column(name = "expires_at", nullable = false, updatable = false)
    private LocalDateTime expiresAt;

    public RefreshToken(long userId, String token, LocalDateTime expiresAt) {
        this.userId = userId;
        this.token = token;
        this.expiresAt = expiresAt;
    }

    public static RefreshToken from(long userId, TokenInfo tokenInfo) {
        return new RefreshToken(
                userId,
                tokenInfo.refreshToken(),
                toLocalDateTime(tokenInfo.refreshTokenExpiresIn())
        );
    }
}
