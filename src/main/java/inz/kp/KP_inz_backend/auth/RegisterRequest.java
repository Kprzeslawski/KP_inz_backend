package inz.kp.KP_inz_backend.auth;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {

    private String email;
    private String password;
    private String name;
    private String surname;
    private String addres;
}
