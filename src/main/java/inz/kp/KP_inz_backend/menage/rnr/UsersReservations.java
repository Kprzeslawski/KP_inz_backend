package inz.kp.KP_inz_backend.menage.rnr;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UsersReservations {
    String email;
    LocalDate date;
    int time;
    int table;
}
