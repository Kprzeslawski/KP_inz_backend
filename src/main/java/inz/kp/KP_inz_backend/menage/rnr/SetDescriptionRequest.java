package inz.kp.KP_inz_backend.menage.rnr;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SetDescriptionRequest {
    private String name;
    private String description;
    private String food_type;
    private String addres;

    private String city;

    private int open;
    private int close;
}
