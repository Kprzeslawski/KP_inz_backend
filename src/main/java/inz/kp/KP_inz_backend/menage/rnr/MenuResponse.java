package inz.kp.KP_inz_backend.menage.rnr;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MenuResponse {
    int cost;
    private String name;
    private String inde;
    private String desc;
    private Integer id;
}
