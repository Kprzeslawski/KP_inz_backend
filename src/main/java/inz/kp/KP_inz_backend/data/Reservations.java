package inz.kp.KP_inz_backend.data;

import inz.kp.KP_inz_backend.User.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "resrevations")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Reservations {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;

    private int time;
    private LocalDate day;
    private int table_size;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "rest_id", referencedColumnName = "Id")
    private Restaurant restaurant;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "Id")
    private User user;

    public Reservations(
            int time,
            LocalDate date,
            int table,
            Restaurant restaurant,
            User user
    ){
        this.time = time;
        this.day = date;
        this.table_size = table;
        this.restaurant = restaurant;
        this.user = user;
    }

}
