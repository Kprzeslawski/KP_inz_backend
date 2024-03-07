package inz.kp.KP_inz_backend.data;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant,Long> {
    //QUERY
    //Restaurant - class name [Entity]
    //r.owner.Id - nesting objects with dependency

    @Query("SELECT r FROM Restaurant r WHERE r.owner.Id = ?1")
    Optional<Restaurant> findByUserId(Integer oId);

    //TODO //@Modifying //https://www.baeldung.com/spring-data-jpa-query
    @Transactional
    @Modifying
    @Query("UPDATE Restaurant r SET r.name = ?2, r.description = ?3, r.food_type = ?4, r.addres = ?5, r.open = ?6, r.close = ?7, r.city = ?8 WHERE r.id = ?1")
    int updateRecord(
            Integer id,
            String name,
            String desc,
            FoodType food_type,
            String addres,
            Integer open,
            Integer close,
            Cities city
    );

    @Transactional
    @Modifying
    @Query("UPDATE Restaurant r SET r.valid = true WHERE r.owner.Id = ?1")
    int setValidByOwner(Integer uID);

    @Query("SELECT r FROM Restaurant r WHERE r.valid = true")
    List<Restaurant> findAllValid();
}
