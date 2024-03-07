package inz.kp.KP_inz_backend.data;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "rating")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Ratings {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "rest_id", referencedColumnName = "Id")
    private Restaurant restaurant;

    private int stars_geted;
    private int reviews;

    @OneToMany(mappedBy = "ratings")
    private List<Reviews> reviewsList;

    public Ratings(
            Restaurant rest
    ){
        this.restaurant = rest;
        this.stars_geted = 0;
        this.reviews = 0;
    }
}
