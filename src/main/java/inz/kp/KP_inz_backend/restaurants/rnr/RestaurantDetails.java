package inz.kp.KP_inz_backend.restaurants.rnr;

import inz.kp.KP_inz_backend.menage.rnr.GetTablesResponse;
import inz.kp.KP_inz_backend.menage.rnr.MenuResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantDetails {
    String name;
    String description;
    String food_type;
    String addres;
    Integer open;
    Integer close;
    Integer r_count;
    Float rating;
    //GetTablesResponse tables;
    //List<MenuResponse> menu;
}
