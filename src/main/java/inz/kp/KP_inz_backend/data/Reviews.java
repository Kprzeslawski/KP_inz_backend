package inz.kp.KP_inz_backend.data;

import inz.kp.KP_inz_backend.User.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "reviews")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Reviews {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "rating_id", referencedColumnName = "Id")
    private Ratings ratings;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "reviewer_id", referencedColumnName = "Id")
    private User user;

//    private int ratingId;
//    private int reviewerId;

    private int rating_val;
    private String description;

    public Reviews(
            Ratings ratings,
            User u,
            int rating,
            String description
    ){
        this.ratings = ratings;
        this.user = u;
        this.rating_val = rating;
        this.description = description;
    }

}
