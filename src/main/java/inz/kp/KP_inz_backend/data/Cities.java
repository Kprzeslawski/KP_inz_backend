package inz.kp.KP_inz_backend.data;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "cities")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Cities
{

    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;

    private String city_name;

    @OneToMany(mappedBy = "city")
    private List<Restaurant> restaurants;
    public Cities(
        String cn
    ){
        this.city_name = cn;
    }
}
