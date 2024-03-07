package inz.kp.KP_inz_backend.menage.rnr;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SetTablesRequest {
    private int table_size;
    private int table_count;
}
