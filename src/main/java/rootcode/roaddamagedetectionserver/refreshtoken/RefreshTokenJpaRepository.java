package rootcode.roaddamagedetectionserver.refreshtoken;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface RefreshTokenJpaRepository extends JpaRepository<RefreshToken, Long> {
    @Query("""
            select case when count(rt) > 0 then true else false end
            from RefreshToken rt
            where rt.userId = :userId
            and rt.token = :token
            and rt.expiresAt > :now
            """
    )
    boolean isRefreshTokenExists(long userId, String token, long now);
}
