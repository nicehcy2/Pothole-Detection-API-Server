package rootcode.roaddamagedetectionserver.alarm.controller.response;

import rootcode.roaddamagedetectionserver.alarm.entity.Alarm;

import java.time.LocalDateTime;

public record AlarmResponseDTO(
        long alarmId,
        String title,
        String content,
        LocalDateTime createdAt
) {
    public static AlarmResponseDTO of(Alarm alarm) {
        return new AlarmResponseDTO(
                alarm.getId(),
                alarm.getTitle(),
                alarm.getContent(),
                alarm.getCreatedAt()
        );
    }
}
