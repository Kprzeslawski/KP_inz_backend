package inz.kp.KP_inz_backend.data;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "food_types")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FoodType {

    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;

    private String food_name;

    @OneToMany(mappedBy = "food_type")
    private List<Restaurant> restaurants;
    public FoodType(
            String fn
    ){
        this.food_name = fn;
    }
}
