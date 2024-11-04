package rootcode.roaddamagedetectionserver.refreshtoken;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import rootcode.roaddamagedetectionserver.jwt.TokenInfo;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class RefreshTokenTest {
    static final long ONE_MINUTES = 60 * 1000L;
    static final long ONE_HOUR = 60 * 1000 * 60L;

    @DisplayName("RefreshToken.from은 TokenInfo를 받아 RefreshToken 객체를 생성한다")
    @Test
    void from() {
        // given
        LocalDateTime now = LocalDateTime.of(2024, 7, 16, 12, 0, 0);

        String wrongTokenValue = "WrongAccessToken";
        String refreshTokenValue = "abc";

        TokenInfo tokenInfo = TokenInfo.builder()
                .accessToken(wrongTokenValue)
                .refreshToken(refreshTokenValue)
                .accessTokenExpiresIn(addTime(now, ONE_MINUTES))
                .refreshTokenExpiresIn(addTime(now, ONE_HOUR))
                .build();

        // when
        RefreshToken refreshToken = RefreshToken.from(1L, tokenInfo);

        // then
        assertThat(refreshToken.getToken()).isEqualTo(refreshTokenValue);
        assertThat(refreshToken.getToken()).isNotEqualTo(wrongTokenValue);
        assertThat(refreshToken.getExpiresAt()).isEqualTo(now.plusHours(1));
    }

    private long addTime(LocalDateTime base, long time) {
        return Timestamp.valueOf(base).getTime() + time;
    }
}