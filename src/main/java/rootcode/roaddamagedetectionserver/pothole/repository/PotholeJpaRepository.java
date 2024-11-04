package rootcode.roaddamagedetectionserver.pothole.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import rootcode.roaddamagedetectionserver.pothole.entity.Pothole;

import java.util.List;

@Repository
public interface PotholeJpaRepository extends JpaRepository<Pothole, Long> {
    @Query(value = """
                        select *
                        from rootcode.pothole p
                        where (p.is_fixed = :isFixed and p.is_valid = true)
                        order by p.id
                        limit :pageSize offset :pageSize * :pageNumber
            """, nativeQuery = true)
    List<Pothole> findByIsFixed(boolean isFixed, int pageSize, int pageNumber);

    @Query(value = """
                    select *
                    from rootcode.pothole p
                    where (:isFixed is null or p.is_fixed = :isFixed)
                    order by p.id
                    limit :pageSize offset :pageSize * :pageNumber
            """, nativeQuery = true)
    List<Pothole> findAllPotholes(Boolean isFixed, int pageSize, int pageNumber);

    @Query(value = """
                    select *
                    from rootcode.pothole p
                    where ((:isFixed is null or p.is_fixed = :isFixed) and (p.is_valid = true))
                    order by p.id
                    limit :pageSize offset :pageSize * :pageNumber
            """, nativeQuery = true)
    List<Pothole> findAllValidPotholes(Boolean isFixed, int pageSize, int pageNumber);

    @Query("""
            update Pothole p
            set p.isValid = true, p.isAiVerified = true
            where p.id in :validPotholeIds
            """)
    @Modifying
    void validatePotholes(List<Long> validPotholeIds);

    @Query("""
            update Pothole p
            set p.isValid = false, p.isAiVerified = true
            where p.id in :invalidPotholeIds
            """)
    @Modifying
    void invalidatePotholes(List<Long> invalidPotholeIds);

    @Modifying
    @Query("DELETE FROM Pothole p WHERE p.id IN :invalidPotholeIds")
    void deletePotholes(List<Long> invalidPotholeIds);

    @Query("""
            select count(p)
            from Pothole p
            where (:isFixed is null or p.isFixed = :isFixed)
            """)
    int countAllByIsFixed(Boolean isFixed);

    @Query("""
            select count(p)
            from Pothole p
            where ((:isFixed is null or p.isFixed = :isFixed) and (p.isValid = true))
            """)
    int countAllValidByIsFixed(Boolean isFixed);

    @Query("SELECT CASE WHEN COUNT(p) > 0 THEN true ELSE false END FROM Pothole p WHERE p.geohash = :geohash")
    boolean existsByGeohash(String geohash);
}
