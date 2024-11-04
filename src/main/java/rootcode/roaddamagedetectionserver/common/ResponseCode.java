package rootcode.roaddamagedetectionserver.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.Optional;
import java.util.function.Predicate;

@Getter
@RequiredArgsConstructor
public enum ResponseCode {

    // 정상 code
    OK(HttpStatus.OK, "2000", "Ok"),

    // Common Error
    _INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON000", "서버 에러, 관리자에게 문의 바랍니다."),
    _BAD_REQUEST(HttpStatus.BAD_REQUEST, "COMMON001", "잘못된 요청입니다."),
    _UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "COMMON002", "로그인이 필요합니다."),
    _METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "COMMON003", "지원하지 않는 Http Method 입니다."),
    _FORBIDDEN(HttpStatus.FORBIDDEN, "COMMON004", "금지된 요청입니다."),

    // Pothole Error
    POTHOLE_NOT_FOUND(HttpStatus.NOT_FOUND, "POTHOLE4004", "포트홀이 없습니다."),

    // User Error
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "USER4004", "유저가 없습니다."),

    // Report Error
    REPORT_NOT_FOUND(HttpStatus.NOT_FOUND, "REPORT4004", "신고 내용이 없습니다"),

    // Alarm Error
    ALARM_NOT_FOUND(HttpStatus.NOT_FOUND, "ALARM4004", "해당 알람이 없습니다"),

    // Region Error
    SIDO_NOT_FOUND(HttpStatus.NOT_FOUND, "REPORT4041", "시도 지역이 없습니다"),
    SIGUNGU_NOT_FOUND(HttpStatus.NOT_FOUND, "REPORT4042", "시군구 지역이 없습니다"),
    EUPMYEONDONG_NOT_FOUND(HttpStatus.NOT_FOUND, "REPORT4043", "읍면동 지역이 없습니다");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    public String getMessage(Throwable e) {
        return this.getMessage(this.message + " - " + e.getMessage());
        // 결과 예시 - "Validation error - Reason why it isn't valid"
    }

    public String getMessage(String message) {
        return Optional.ofNullable(message)
                .filter(Predicate.not(String::isBlank))
                .orElse(this.getMessage());
    }
}
