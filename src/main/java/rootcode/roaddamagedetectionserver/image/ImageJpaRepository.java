package rootcode.roaddamagedetectionserver.image;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ImageJpaRepository extends JpaRepository<Image, Long> {
    Optional<Image> findByUrl(String url);

    Optional<Image> findByPotholeId(long potholeId);

    @Query("""
            SELECT i
            FROM Image i
            JOIN FETCH  i.pothole p
            WHERE p.isAiVerified = false
            """)
    List<Image> findAllUnverifiedPotholeImages();

    @Query("""
            SELECT i
            FROM Image i
            WHERE i.pothole.id IN :potholeIds
            ORDER BY i.pothole.id
            """)
    List<Image> findByPotholeIds(List<Long> potholeIds);

    @Modifying
    @Query("""
        update Image i
        set i.url = :newUrl
        where i.pothole.id = :potholeId
        """)
    void updateImageUrls(Long potholeId, String newUrl);
}
