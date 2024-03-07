package inz.kp.KP_inz_backend.restaurants;


import inz.kp.KP_inz_backend.data.Restaurant;
import inz.kp.KP_inz_backend.data.Reviews;
import inz.kp.KP_inz_backend.menage.rnr.MenuResponse;
import inz.kp.KP_inz_backend.menage.rnr.SetDescriptionRequest;
import inz.kp.KP_inz_backend.restaurants.rnr.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/restaurant")
@RequiredArgsConstructor
public class RestaurantsController {

    @Autowired
    private RestaurantsService restaurantsService;

    @GetMapping("/restaurants")
    public ResponseEntity<List<RestaurantInfo>> getRestaurants(){
        return ResponseEntity.ok(restaurantsService.getRestaurants());
    }

    @GetMapping("/restaurant/{id}")
    public ResponseEntity<Optional<RestaurantDetails>> getRestaurant(
            @PathVariable Integer id
    ){
        return ResponseEntity.ok(restaurantsService.getRestaurant(id));
    }

//    @PostMapping("/review")
//    public ResponseEntity<Long> addReview(
//            @RequestBody AddReview request
//    ){
//        return ResponseEntity.ok(restaurantsService.addReview(request));
//    }

    @GetMapping("/reviews/{id}")
    public ResponseEntity<List<ReviewRec>> showReviews(
            @PathVariable Integer id
    ){
        return ResponseEntity.ok(restaurantsService.showReviews(id));
    }

//    @GetMapping("/is_rating_set/{id}")
//    public ResponseEntity<Boolean> isRatingSet(
//            @PathVariable Integer id
//    ){
//        return ResponseEntity.ok(restaurantsService.isRatingSet(id));
//    }

//    @GetMapping("/av_reservations/{id}/{date}/{tab}")
//    public ResponseEntity<List<Integer>> showAvReservations(
//            @PathVariable Integer id,
//            @PathVariable LocalDate date,
//            @PathVariable Integer tab
//    ){
//        return ResponseEntity.ok(restaurantsService.showAvReservationsD(id,date,tab));
//    }
//    @PostMapping("/reservation")
//    public ResponseEntity<Long> makeReservation(
//            @RequestBody MakeReservation request
//    ){
//        return ResponseEntity.ok(restaurantsService.makeReservation(request));
//    }

    @GetMapping("/menu/{id}")
    public ResponseEntity<List<MenuResponse>> showMenuId(
            @PathVariable Integer id
    ){
        return ResponseEntity.ok(restaurantsService.showMenuId(id));
    }

//    @GetMapping("/my_reserv")
//    public ResponseEntity<List<ReservRec>> showMyReserv(){
//        return ResponseEntity.ok(restaurantsService.showMyReserv());
//    }
//
//    @DeleteMapping("/my_reserv/{id}")
//    public ResponseEntity<Boolean> delMyReserv(
//            @PathVariable Integer id
//    ){
//        return ResponseEntity.ok(restaurantsService.deleteMyReserv(id));
//    }

//    @GetMapping("/pos_tables/{id}")
//    public ResponseEntity<List<Integer>> getPosTables(
//            @PathVariable Integer id
//    ){
//        return ResponseEntity.ok(restaurantsService.getPosTables(id));
//    }

    @GetMapping("/cities")
    public ResponseEntity<List<String>> getCities(){
        return ResponseEntity.ok(restaurantsService.getCities());
    }


    @GetMapping("/food_types")
    public ResponseEntity<List<String>> getFoodTypes(){
        return ResponseEntity.ok(restaurantsService.getFoodTypes());
    }
}
