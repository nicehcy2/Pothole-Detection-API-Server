package rootcode.roaddamagedetectionserver.user.controller.response;

import lombok.Builder;

@Builder
public record UserInfoResponseDto(
        long userId,
        String name,
        String email,
        String phoneNumber,
        String authGrade
) {
}
