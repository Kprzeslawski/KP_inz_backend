package inz.kp.KP_inz_backend.User;

import inz.kp.KP_inz_backend.data.Menu;
import inz.kp.KP_inz_backend.data.Reservations;
import inz.kp.KP_inz_backend.data.Restaurant;
import inz.kp.KP_inz_backend.data.Reviews;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;

    private String email;
    private String password;

    private String name;
    private String surname;
    private String addres;


    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToOne(mappedBy = "owner")
    private Restaurant restaurant;

    @OneToMany(mappedBy = "user")
    private List<Reviews> reviewsList;

    @OneToMany(mappedBy = "user")
    private List<Reservations> reservations;

    public User(String userEmail,String password,String name, String surname, String addres
        ){
        this.email = userEmail;
        this.password = password;
        this.role = Role.USER;
        this.name = name;
        this.surname = surname;
        this.addres = addres;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()),new SimpleGrantedAuthority("ID_"+Id));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
