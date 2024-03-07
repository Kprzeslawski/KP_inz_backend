package inz.kp.KP_inz_backend.data;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TablesRepository extends JpaRepository<Tables,Long> {

    @Query("SELECT t FROM Tables t WHERE t.restaurant.owner.Id = ?1")
    List<Tables> findByOwnerId(int uID);

    @Transactional
    @Modifying
    @Query("UPDATE Tables t SET " +
            "t.table_count = ?3 " +
            "WHERE t.restaurant = ?1 " +
            "AND t.table_size = ?2")
    int updateTablesCount(
            Restaurant rest,
            Integer table_size,
            Integer table_count
    );

    @Query("SELECT t.table_count FROM Tables t WHERE t.restaurant.ID = ?2 AND t.table_size = ?1")
    Optional<Integer> getTableCount(
            Integer size,
            Integer rest_id
    );

    @Transactional
    @Modifying
    @Query("DElETE FROM Tables t WHERE t.table_size = ?1 AND t.restaurant = ?2")
    int deleteRecord(Integer table_size, Restaurant rest);
}
