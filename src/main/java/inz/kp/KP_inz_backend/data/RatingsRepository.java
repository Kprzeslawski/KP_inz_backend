package inz.kp.KP_inz_backend.data;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface RatingsRepository  extends JpaRepository<Ratings,Long> {
    @Query("SELECT r FROM Ratings r WHERE r.restaurant.Id = ?1")
    Optional<Ratings> findByRestId(Integer id);

    @Transactional
    @Modifying
    @Query("""
        UPDATE Ratings r
        SET r.stars_geted = r.stars_geted + ?2, r.reviews = r.reviews + 1
        WHERE r.Id = ?1
        """)
    void adjustRating(int id, int rating);
}
