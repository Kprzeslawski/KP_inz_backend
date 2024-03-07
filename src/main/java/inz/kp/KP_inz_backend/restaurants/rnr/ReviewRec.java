package inz.kp.KP_inz_backend.restaurants.rnr;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewRec {
    String description;
    int rating;
    String rev_username;
}
