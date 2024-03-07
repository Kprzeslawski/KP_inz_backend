package inz.kp.KP_inz_backend.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CitiesRepository extends JpaRepository<Cities,Long>  {

    @Query("SELECT c FROM Cities c WHERE c.city_name = ?1")
    Optional<Cities> findByCity_name(String request);
}
