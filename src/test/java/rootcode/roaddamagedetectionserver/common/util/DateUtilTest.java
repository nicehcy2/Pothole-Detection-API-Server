package rootcode.roaddamagedetectionserver.common.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

class DateUtilTest {
    @DisplayName("toTimestamp 메서드는 LocalDateTime을 받아 timestamp로 변환한다")
    @Test
    void toTimestamp() {
        // given
        long timestamp = new Date().getTime();
        LocalDateTime localDateTime = DateUtil.toLocalDateTime(timestamp);

        // when
        long result = DateUtil.toTimestamp(localDateTime);

        // then
        assertThat(result).isEqualTo(timestamp);
    }
}