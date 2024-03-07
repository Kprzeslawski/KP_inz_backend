package inz.kp.KP_inz_backend.admin;

import inz.kp.KP_inz_backend.admin.rnr.CityReq;
import inz.kp.KP_inz_backend.admin.rnr.FoodTypeReq;
import inz.kp.KP_inz_backend.admin.rnr.UpdateRoleRequest;
import inz.kp.KP_inz_backend.admin.rnr.UserRecord;
import inz.kp.KP_inz_backend.menage.rnr.SetTablesRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    @Autowired
    private AdminService adminService;

    @GetMapping("/users")
    public ResponseEntity<List<UserRecord>> getUsers(){
        return ResponseEntity.ok(adminService.getUsers());
    }

    @PostMapping("/user_role")
    public ResponseEntity<Long> setUserRole(
            @RequestBody UpdateRoleRequest request
    ){
        return ResponseEntity.ok(adminService.setUserRole(request));
    }

    @PostMapping("/city")
    public ResponseEntity<Long> addCity(
            @RequestBody CityReq request
    ){
        return  ResponseEntity.ok(adminService.addCity(request));
    }

    @PostMapping("/food_type")
    public ResponseEntity<Long> addFoodType(
            @RequestBody FoodTypeReq request
    ){
        return  ResponseEntity.ok(adminService.addFoodType(request));
    }


}
