package inz.kp.KP_inz_backend.loggedUsers;

import inz.kp.KP_inz_backend.restaurants.rnr.AddReview;
import inz.kp.KP_inz_backend.restaurants.rnr.MakeReservation;
import inz.kp.KP_inz_backend.restaurants.rnr.ReservRec;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/logged_users")
@RequiredArgsConstructor
public class LoggedUsersController {

    @Autowired
    private LoggedUsersService loggedUsersService;

    @PostMapping("/review")
    public ResponseEntity<Long> addReview(
            @RequestBody AddReview request
    ){
        return ResponseEntity.ok(loggedUsersService.addReview(request));
    }

    @GetMapping("/rating_set/{id}")
    public ResponseEntity<Boolean> isRatingSet(
            @PathVariable Integer id
    ){
        return ResponseEntity.ok(loggedUsersService.isRatingSet(id));
    }

    @GetMapping("/av_reservations/{id}/{date}/{tab}")
    public ResponseEntity<List<Integer>> showAvReservations(
            @PathVariable Integer id,
            @PathVariable LocalDate date,
            @PathVariable Integer tab
    ){
        return ResponseEntity.ok(loggedUsersService.showAvReservationsD(id,date,tab));
    }

    @PostMapping("/reservation")
    public ResponseEntity<Long> makeReservation(
            @RequestBody MakeReservation request
    ){
        return ResponseEntity.ok(loggedUsersService.makeReservation(request));
    }

    @GetMapping("/my_reserv")
    public ResponseEntity<List<ReservRec>> showMyReserv(){
        return ResponseEntity.ok(loggedUsersService.showMyReserv());
    }

    @DeleteMapping("/my_reserv/{id}")
    public ResponseEntity<Boolean> delMyReserv(
            @PathVariable Integer id
    ){
        return ResponseEntity.ok(loggedUsersService.deleteMyReserv(id));
    }

    @GetMapping("/pos_tables/{id}")
    public ResponseEntity<List<Integer>> getPosTables(
            @PathVariable Integer id
    ){
        return ResponseEntity.ok(loggedUsersService.getPosTables(id));
    }

}
