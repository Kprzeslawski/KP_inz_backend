package inz.kp.KP_inz_backend.restaurants.rnr;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantInfo {
    private Integer id;
    private String name;
    private String food_type;
    private String city;
    private String addres;

    private Integer r_count;
    private Float rating;
}
