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
public class ReservRec {
    int Id;
    LocalDate date;
    Integer time;
    Integer table;
    String res_name;
}
