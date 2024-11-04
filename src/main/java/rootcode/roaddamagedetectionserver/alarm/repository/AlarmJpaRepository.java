package rootcode.roaddamagedetectionserver.alarm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rootcode.roaddamagedetectionserver.alarm.entity.Alarm;

@Repository
public interface AlarmJpaRepository extends JpaRepository<Alarm, Long> {
}
