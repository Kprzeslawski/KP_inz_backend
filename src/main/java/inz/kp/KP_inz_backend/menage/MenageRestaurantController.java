package inz.kp.KP_inz_backend.menage;

import inz.kp.KP_inz_backend.menage.rnr.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/menage")
@RequiredArgsConstructor
public class MenageRestaurantController {

    @Autowired
    private MenageRestaurantService menageRestaurantService;

    @PostMapping("/description")
    public ResponseEntity<Long> setDescription(
            @RequestBody SetDescriptionRequest request
    ) {
        return ResponseEntity.ok(menageRestaurantService.setDescription(request));
    }

    @GetMapping("/description")
    public ResponseEntity<GetDescriptionResponse> getDescription() {
        return ResponseEntity.ok(menageRestaurantService.getDescription());
    }

    @PostMapping("/menu")
    public ResponseEntity<Long> addToMenu(
            @RequestBody AddToMenuRequest request
            ) {
        return ResponseEntity.ok(menageRestaurantService.addToMenu(request));
    }

    @GetMapping("/menu")
    public ResponseEntity<List<MenuResponse>> showMenu(){
        return ResponseEntity.ok(menageRestaurantService.showMenu());
    }

    @DeleteMapping("/menu")
    public ResponseEntity<Long> delFromMenu(
            @RequestBody Integer id
    ){
        return ResponseEntity.ok(menageRestaurantService.delFromMenu(id));
    }

    @PostMapping("/tables")
    public ResponseEntity<Long> setTables(
            @RequestBody List<SetTablesRequest> request
            ) {
        return ResponseEntity.ok(menageRestaurantService.setTables(request));
    }

    @GetMapping("/tables")
    public ResponseEntity<List<GetTablesResponse>> getTables(){
        return ResponseEntity.ok(menageRestaurantService.getTables());
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<UsersReservations>> showReservations(){
        return ResponseEntity.ok(menageRestaurantService.showReservations());
    }

    @GetMapping("/validate")
    public ResponseEntity<Long> validate(){
        return ResponseEntity.ok(menageRestaurantService.validate());
    }

    @GetMapping("valid")
    public ResponseEntity<Boolean> isValid(){
        return ResponseEntity.ok(menageRestaurantService.isValid());
    }
}
