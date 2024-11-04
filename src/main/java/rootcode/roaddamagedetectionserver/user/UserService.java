package rootcode.roaddamagedetectionserver.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import rootcode.roaddamagedetectionserver.common.ResponseCode;
import rootcode.roaddamagedetectionserver.user.controller.exception.handler.UserHandler;
import rootcode.roaddamagedetectionserver.common.util.AuthUtil;
import rootcode.roaddamagedetectionserver.user.controller.request.CreateUserRequestDTO;
import rootcode.roaddamagedetectionserver.user.controller.response.UserInfoResponseDto;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserJpaRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User createUser(CreateUserRequestDTO request) {
        validateDuplicateEmail(request.email());

        User user = User.builder()
                .name(request.name())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .phoneNumber(request.phoneNumber())
                .auth(AuthGrade.of(request.auth()))
                .build();

        return userRepository.save(user);
    }

    private void validateDuplicateEmail(String email) {
        userRepository.findByEmail(email)
                .ifPresent(user -> {
                    throw new IllegalArgumentException("이미 존재하는 사용자입니다.");
                });
    }

    public User login(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserHandler(ResponseCode.USER_NOT_FOUND));

        if (!user.isPasswordMatch(password, passwordEncoder)) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        return user;
    }

    public User findById(long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserHandler(ResponseCode.USER_NOT_FOUND));
    }

    public UserInfoResponseDto getUserInfo() {

        long userId =  AuthUtil.getCurrentUserId();
        User user = findById(userId);

        return UserInfoResponseDto.builder()
                .userId(userId)
                .name(user.getName())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .authGrade(user.getAuth().toString())
                .build();
    }
}
