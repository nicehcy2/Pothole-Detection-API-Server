package rootcode.roaddamagedetectionserver.refreshtoken;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rootcode.roaddamagedetectionserver.jwt.TokenInfo;
import rootcode.roaddamagedetectionserver.user.User;

import java.time.LocalDateTime;

import static rootcode.roaddamagedetectionserver.common.util.DateUtil.toTimestamp;

@RequiredArgsConstructor
@Service
public class RefreshTokenService {
    private final RefreshTokenJpaRepository refreshTokenRepository;

    @Transactional(readOnly = true)
    public boolean isRefreshTokenExists(long userId, String token, LocalDateTime now) {
        return refreshTokenRepository.isRefreshTokenExists(userId, token, toTimestamp(now));
    }

    @Transactional
    public void save(User user, TokenInfo tokenInfo) {
        RefreshToken token = RefreshToken.from(user.getId(), tokenInfo);

        refreshTokenRepository.save(token);
    }
}
