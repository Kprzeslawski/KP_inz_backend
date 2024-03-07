package inz.kp.KP_inz_backend.data;

import inz.kp.KP_inz_backend.restaurants.rnr.ReservRec;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface ReservationsRepository extends JpaRepository<Reservations,Long> {
    @Query("SELECT r FROM Reservations r WHERE r.restaurant.Id = ?1 AND r.day = ?2 AND r.table_size = ?3")
    List<Reservations> showTableInDay(int id, LocalDate date, Integer tab);

    @Query("SELECT r FROM Reservations r WHERE r.restaurant.Id = ?1 AND r.day > ?2")
    List<Reservations> showNextReservations(int id, LocalDate today);

    @Query("SELECT r FROM Reservations r WHERE r.user.Id = ?1")
    List<Reservations> userReserv(int uID);

    @Query("SELECT r FROM Reservations r WHERE r.user.Id = ?1 AND r.day = ?2")
    List<Reservations> userReservInDay(int uID, LocalDate day);

    @Transactional
    @Modifying
    @Query("DElETE FROM Reservations r WHERE r.Id = ?1 AND r.user.Id = ?2")
    Integer deleteReserv(Integer id, int uID);
}
