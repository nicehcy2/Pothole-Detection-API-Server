package rootcode.roaddamagedetectionserver.management;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ManagementJpaRepository extends JpaRepository<Management, Long> {
}
