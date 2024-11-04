package rootcode.roaddamagedetectionserver.alarm.application;

import rootcode.roaddamagedetectionserver.alarm.controller.request.AlarmRequestDTO;
import rootcode.roaddamagedetectionserver.alarm.controller.response.AlarmResponseDTO;

import java.util.List;

public interface AlarmService {

    long createAlarm(long userId, AlarmRequestDTO alarmRequestDTO);

    List<AlarmResponseDTO> getAlarms();

    long deleteAlarm(long alarmId);
}
