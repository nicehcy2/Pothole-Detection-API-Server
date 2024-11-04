package rootcode.roaddamagedetectionserver.alarm.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rootcode.roaddamagedetectionserver.alarm.application.AlarmService;
import rootcode.roaddamagedetectionserver.alarm.controller.request.AlarmRequestDTO;
import rootcode.roaddamagedetectionserver.alarm.controller.response.AlarmResponseDTO;
import rootcode.roaddamagedetectionserver.annotation.NeedLogin;

import java.util.List;

@Tag(description = "알람 관련 API", name = "Alarm")
@RequiredArgsConstructor
@RestController
@RequestMapping("/alarms")
public class AlarmController {

    private final AlarmService alarmService;

    @NeedLogin
    @Operation(summary = "알람 생성")
    @PostMapping("/{userId}")
    public long createAlarm(
            @PathVariable("userId") long userId,
            @RequestBody AlarmRequestDTO alarmRequestDTO
    ) {
        return alarmService.createAlarm(userId, alarmRequestDTO);
    }

    @NeedLogin
    @Operation(summary = "알람 조회")
    @GetMapping
    public List<AlarmResponseDTO> getAlarms() {

        return alarmService.getAlarms();
    }

    @NeedLogin
    @Operation(summary = "알람 삭제")
    @DeleteMapping("/{alarmId}")
    public long deleteAlarm(@PathVariable("alarmId") long alarmId) {

        return alarmService.deleteAlarm(alarmId);
    }
}
