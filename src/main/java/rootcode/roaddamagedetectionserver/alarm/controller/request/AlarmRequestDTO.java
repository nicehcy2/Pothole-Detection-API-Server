package rootcode.roaddamagedetectionserver.alarm.controller.request;

public record AlarmRequestDTO(
        long userId,
        String title,
        String content
) {

}
