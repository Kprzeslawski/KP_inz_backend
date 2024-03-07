package inz.kp.KP_inz_backend.data;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tables")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Tables {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;

    private int table_size;
    private int table_count;


    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "rest_id", referencedColumnName = "Id")
    private Restaurant restaurant;

    public Tables(
            Restaurant restaurant,
            int table_size,
            int table_count
    )
    {
        this.restaurant = restaurant;
        this.table_size = table_size;
        this.table_count = table_count;
    }
}
