package inz.kp.KP_inz_backend.admin;

import inz.kp.KP_inz_backend.User.Role;
import inz.kp.KP_inz_backend.User.UserRepository;
import inz.kp.KP_inz_backend.admin.rnr.CityReq;
import inz.kp.KP_inz_backend.admin.rnr.FoodTypeReq;
import inz.kp.KP_inz_backend.admin.rnr.UpdateRoleRequest;
import inz.kp.KP_inz_backend.admin.rnr.UserRecord;
import inz.kp.KP_inz_backend.data.Cities;
import inz.kp.KP_inz_backend.data.CitiesRepository;
import inz.kp.KP_inz_backend.data.FoodType;
import inz.kp.KP_inz_backend.data.FoodTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.util.EnumUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CitiesRepository citiesRepository;

    @Autowired
    private FoodTypeRepository foodTypeRepository;

    public List<UserRecord> getUsers() {
        return userRepository
                .findAll()
                .stream()
                .map(
                record -> new UserRecord(
                        record.getId(),
                        record.getEmail(),
                        record.getRole().toString()
                        )
        ).toList();
    }


    public Long setUserRole(UpdateRoleRequest request) {
        for(Role r : Role.values())
            if(r.name().equals(request.getRole()))
                return (long) userRepository.updateRole(
                  request.getId(),
                  r
                );
        return 1L;
    }

    public Long addCity(CityReq request) {
        if(citiesRepository.findByCity_name(request.getName()).isPresent())
            return -1L;

        citiesRepository.save(
                        new Cities(
                                request.getName()
                        ));
        return 0L;
    }

    public Long addFoodType(FoodTypeReq request) {
        if(foodTypeRepository.findByFood_name(request.getName()).isPresent())
            return -1L;

        foodTypeRepository.save(
                new FoodType(
                        request.getName()
                ));
        return 0L;
    }
}
