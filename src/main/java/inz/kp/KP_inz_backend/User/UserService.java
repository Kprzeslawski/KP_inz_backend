package inz.kp.KP_inz_backend.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    public List<User> allUsers(){
        return userRepository.findAll();
    }
}
