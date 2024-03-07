package inz.kp.KP_inz_backend.restaurants.rnr;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AvReservations {
    public Integer tab_count;
    public List<AvResrvByDay> days;
}

