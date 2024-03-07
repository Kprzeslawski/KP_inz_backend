package inz.kp.KP_inz_backend.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface FoodTypeRepository extends JpaRepository<FoodType,Long> {

    @Query("SELECT f FROM FoodType f WHERE f.food_name = ?1")
    Optional<FoodType> findByFood_name(String request);
}
