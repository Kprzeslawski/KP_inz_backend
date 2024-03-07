package inz.kp.KP_inz_backend.admin.rnr;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRecord {
    int Id;
    String email;
    String role;
}
