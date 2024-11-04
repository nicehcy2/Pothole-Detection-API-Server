package rootcode.roaddamagedetectionserver.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthService {
    private final AuthenticationManagerBuilder authManagerBuilder;

    public Authentication getAuthentication(User user) {
        var authToken = new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());

        // 2. 실제로 검증 (사용자 비밀번호 체크) 이 이루어지는 부분
        //    authenticate 메서드가 실행이 될 때 CustomUserDetailsService 에서 만들었던 loadUserByUsername 메서드가 실행됨
        return authManagerBuilder.getObject().authenticate(authToken);
    }
}
