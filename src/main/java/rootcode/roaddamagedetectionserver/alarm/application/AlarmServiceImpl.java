package rootcode.roaddamagedetectionserver.alarm.application;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rootcode.roaddamagedetectionserver.alarm.controller.request.AlarmRequestDTO;
import rootcode.roaddamagedetectionserver.alarm.controller.response.AlarmResponseDTO;
import rootcode.roaddamagedetectionserver.alarm.entity.Alarm;
import rootcode.roaddamagedetectionserver.alarm.exception.handler.AlarmHandler;
import rootcode.roaddamagedetectionserver.alarm.repository.AlarmJpaRepository;
import rootcode.roaddamagedetectionserver.common.ResponseCode;
import rootcode.roaddamagedetectionserver.common.util.AuthUtil;
import rootcode.roaddamagedetectionserver.user.User;
import rootcode.roaddamagedetectionserver.user.UserJpaRepository;
import rootcode.roaddamagedetectionserver.user.controller.exception.handler.UserHandler;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AlarmServiceImpl implements AlarmService {

    private final AlarmJpaRepository alarmRepository;
    private final UserJpaRepository userJpaRepository;

    @Override
    @Transactional
    public long createAlarm(long userId, AlarmRequestDTO alarmRequestDTO) {

        validateAdminAccess();

        User user = userJpaRepository.findById(userId)
                .orElseThrow(() -> new UserHandler(ResponseCode.USER_NOT_FOUND));

        Alarm alarm = Alarm.builder()
                .title(alarmRequestDTO.title())
                .content(alarmRequestDTO.content())
                .user(user)
                .build();

        alarmRepository.save(alarm);

        return userId;
    }

    @Override
    public List<AlarmResponseDTO> getAlarms() {

        long userId = AuthUtil.getCurrentUserId();

        return alarmRepository.findAll()
                .stream()
                .filter(alarm -> alarm.getUser().getId() == userId)
                .map(AlarmResponseDTO::of)
                .toList();
    }

    @Override
    @Transactional
    public long deleteAlarm(long alarmId) {

        Alarm alarm = alarmRepository.findById(alarmId)
                .orElseThrow(() -> new AlarmHandler(ResponseCode.ALARM_NOT_FOUND));

        alarmRepository.delete(alarm);

        return alarmId;
    }

    private void validateAdminAccess() {
        long userId = AuthUtil.getCurrentUserId();

        User user = userJpaRepository.findById(userId)
                .orElseThrow(() -> new UserHandler(ResponseCode.USER_NOT_FOUND));

        if (!user.isAdmin()) {
            throw new AccessDeniedException("관리자 권한이 필요합니다.");
        }
    }
}
