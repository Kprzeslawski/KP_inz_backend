package inz.kp.KP_inz_backend.data;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "menu")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;

    private int cost;
    private boolean available;

    private String name;
    private String indegriends;
    private String description;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "rest_id", referencedColumnName = "Id")
    private Restaurant restaurant;

    public Menu(
            Restaurant restaurant,
            String name,
            String indegriends,
            String description,
            int cost,
            boolean available
    ){
        this.restaurant = restaurant;
        this.name = name;
        this.indegriends = indegriends;
        this.description = description;
        this.cost = cost;
        this.available = available;
    }

}
