package inz.kp.KP_inz_backend.auth;

import inz.kp.KP_inz_backend.User.User;
import inz.kp.KP_inz_backend.User.UserRepository;
import inz.kp.KP_inz_backend.config.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class AuthService {

    @Autowired
    private UserRepository repository;
    @Autowired
    private final JwtService jwtService;
    @Autowired
    private final AuthenticationManager authenticationManager;
    @Autowired
    private final PasswordEncoder passwordEncoder;

    public AuthResponse register(RegisterRequest request) {
        if(repository.findByEmail(request.getEmail()).isPresent())return null;

        Pattern pattern = Pattern.compile("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}");
        Matcher mat = pattern.matcher(request.getEmail());

        if(!mat.matches())return null;
        if(request.getPassword().length() < 5)return null;

        User user = new User(
                request.getEmail(),
                passwordEncoder.encode(request.getPassword()),
                request.getName(),
                request.getSurname(),
                request.getAddres()
        );
        repository.save(user);
        String jwtToken = jwtService.generateToken(user);

        return new AuthResponse(jwtToken,user.getId(),user.getRole().name());
    }

    public AuthResponse authenticate(AuthRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = repository.findByEmail(request.getEmail())
                .orElseThrow();
        String jwtToken = jwtService.generateToken(user);

        return new AuthResponse(jwtToken,user.getId(),user.getRole().name());
    }
}
