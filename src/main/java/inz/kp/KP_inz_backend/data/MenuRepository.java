package inz.kp.KP_inz_backend.data;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MenuRepository extends JpaRepository<Menu,Long> {
    @Query("SELECT m FROM Menu m WHERE m.restaurant.owner.Id = ?1")
    List<Menu> findAllByUserId(int uID);

    @Transactional
    @Modifying
    @Query("DElETE FROM Menu m WHERE m.Id = ?1 AND m.restaurant.Id = ?2")
    int deleteRecord(Integer id, int id1);
}
