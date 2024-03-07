package inz.kp.KP_inz_backend.data;

import inz.kp.KP_inz_backend.restaurants.rnr.ReservRec;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ReviewsRepository extends JpaRepository<Reviews,Long> {

    @Query("SELECT r FROM Reviews r WHERE r.user.Id = ?1 and r.ratings.restaurant.Id = ?2")
    Optional<Reviews> alreadyAdded(int uID, int restId);

}
