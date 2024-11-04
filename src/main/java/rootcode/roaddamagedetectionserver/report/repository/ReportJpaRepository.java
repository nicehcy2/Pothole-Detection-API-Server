package rootcode.roaddamagedetectionserver.report.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rootcode.roaddamagedetectionserver.report.entity.Report;
import rootcode.roaddamagedetectionserver.user.User;

import java.util.List;

public interface ReportJpaRepository extends JpaRepository<Report, Long> {

    List<Report> findAllByUser(User user);
}
