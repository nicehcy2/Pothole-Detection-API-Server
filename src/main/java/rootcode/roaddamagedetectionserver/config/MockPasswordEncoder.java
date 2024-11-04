package rootcode.roaddamagedetectionserver.config;

import org.springframework.security.crypto.password.PasswordEncoder;


// TODO: 멀쩡한 비밀번호 암호화 방식을 사용해야 합니다.
public class MockPasswordEncoder implements PasswordEncoder {
    @Override
    public String encode(CharSequence rawPassword) {
        return rawPassword.toString();
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return rawPassword.toString().equals(encodedPassword);
    }
}
