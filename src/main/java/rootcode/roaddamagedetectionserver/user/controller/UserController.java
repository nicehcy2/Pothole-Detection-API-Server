package rootcode.roaddamagedetectionserver.user.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import rootcode.roaddamagedetectionserver.annotation.NeedLogin;
import rootcode.roaddamagedetectionserver.jwt.TokenInfo;
import rootcode.roaddamagedetectionserver.jwt.TokenProvider;
import rootcode.roaddamagedetectionserver.refreshtoken.RefreshTokenService;
import rootcode.roaddamagedetectionserver.user.AuthService;
import rootcode.roaddamagedetectionserver.user.User;
import rootcode.roaddamagedetectionserver.user.UserService;
import rootcode.roaddamagedetectionserver.user.controller.request.CreateUserRequestDTO;
import rootcode.roaddamagedetectionserver.user.controller.request.LoginRequestDTO;
import rootcode.roaddamagedetectionserver.user.controller.response.UserInfoResponseDto;

import java.time.LocalDateTime;
import java.util.Map;

@Tag(description = "사용자 관련 API", name = "User")
@RequiredArgsConstructor
@RestController
public class UserController {
    private final UserService userService;
    private final AuthService authService;
    private final TokenProvider tokenProvider;
    private final RefreshTokenService refreshTokenService;

    @Operation(summary = "사용자 생성")
    @PostMapping("/users")
    public Map<String, String> createUser(
            @RequestBody CreateUserRequestDTO request
    ) {
        User user = userService.createUser(request);
        TokenInfo token = createToken(user);

        refreshTokenService.save(user, token);

        return Map.of(
                "accessToken", token.accessToken(),
                "refreshToken", token.refreshToken()
        );
    }

    @Operation(summary = "사용자 로그인")
    @PostMapping("/users/login")
    public Map<String, String> login(
            @RequestBody LoginRequestDTO request
    ) {
        User user = userService.login(request.email(), request.password());
        TokenInfo token = createToken(user);

        refreshTokenService.save(user, token);

        return Map.of(
                "accessToken", token.accessToken(),
                "refreshToken", token.refreshToken()
        );
    }

    @Operation(summary = "토큰 재발급")
    @PostMapping("/users/refresh")
    public ResponseEntity<Void> refresh(
            @RequestBody String refreshToken
    ) {
        long userId = tokenProvider.getSubject(refreshToken);

        if (!refreshTokenService.isRefreshTokenExists(userId, refreshToken, LocalDateTime.now())) {
            return ResponseEntity
                    .badRequest()
                    .build();
        }

        User user = userService.findById(userId);

        TokenInfo token = createToken(user);
        refreshTokenService.save(user, token);

        return createResponseEntityWithCookie(token);
    }

    @NeedLogin
    @Operation(summary = "로그인 된 유저 정보 받아오기")
    @GetMapping("/users")
    public ResponseEntity<UserInfoResponseDto> getUserInfo() {

        return ResponseEntity.ok(userService.getUserInfo());
    }

    private TokenInfo createToken(User user) {
        Authentication auth = authService.getAuthentication(user);
        return tokenProvider.generateToken(auth);
    }

    private ResponseEntity<Void> createResponseEntityWithCookie(TokenInfo token) {
        return ResponseEntity
                .ok()
                .header(HttpHeaders.SET_COOKIE, createAccessTokenCookie(token))
                .header(HttpHeaders.SET_COOKIE, createRefreshTokenCookie(token))
                .build();
    }

    private String createAccessTokenCookie(TokenInfo token) {
        return createCookie("accessToken", token.accessToken(), token.accessTokenExpiresIn())
                .toString();
    }

    private String createRefreshTokenCookie(TokenInfo token) {
        return createCookie("refreshToken", token.refreshToken(), token.refreshTokenExpiresIn())
                .toString();
    }

    private ResponseCookie createCookie(String cookieName, String value, long maxAge) {
        return ResponseCookie.from(cookieName, value)
                .maxAge(maxAge)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .build();
    }
}
