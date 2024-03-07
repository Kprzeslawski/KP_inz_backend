package inz.kp.KP_inz_backend.menage.rnr;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetDescriptionResponse {
    private String name;
    private String description;
    private String food_type;
    private String addres;

    private String city;

    private int open;
    private int close;
}
