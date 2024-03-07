package inz.kp.KP_inz_backend.restaurants.rnr;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MakeReservation {
    int rest_id;
    LocalDate date;
    int table;
    int time;
}
