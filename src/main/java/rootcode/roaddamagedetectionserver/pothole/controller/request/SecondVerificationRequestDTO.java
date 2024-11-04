package rootcode.roaddamagedetectionserver.pothole.controller.request;

import java.util.List;

public record SecondVerificationRequestDTO(
        List<Long> validPotholeIds,
        List<Long> invalidPotholeIds,
        List<String> potholeUrl
) {
}
