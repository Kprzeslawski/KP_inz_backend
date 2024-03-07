package inz.kp.KP_inz_backend.data;

import inz.kp.KP_inz_backend.User.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "restaurants")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;

    // warning about naming ownerId -> owner_id which does not exist
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "owner_id", referencedColumnName = "Id")
    private User owner;
    private String name;
    private String description;

    private String addres;

    //open/close date as military time
    private int open;
    private int close;

    private boolean valid;

    @OneToMany(mappedBy = "restaurant")
    private List<Menu> menu;

    @OneToMany(mappedBy = "restaurant")
    private List<Tables> tables;

    @OneToOne(mappedBy = "restaurant")
    private Ratings ratings;

    @OneToMany(mappedBy = "restaurant")
    private List<Reservations> reservations;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "city_id", referencedColumnName = "Id")
    private Cities city;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "food_id", referencedColumnName = "Id")
    private FoodType food_type;

    public Restaurant(
            User u,
            Cities c,
            String name,
            String desc,
            FoodType food_type,
            String addres,
            int open,
            int close
    ){
        this.owner = u;
        this.city = c;
        this.name = name;
        this.description = desc;
        this.food_type = food_type;
        this.addres = addres;
        this.open = open;
        this.close = close;
        this.valid = false;
    }

}
