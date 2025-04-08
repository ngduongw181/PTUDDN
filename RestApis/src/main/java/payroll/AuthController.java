package payroll;

import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserRepository userRepository;

    public AuthController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/login")
    public String login(@RequestBody User user) {
        Optional<User> existingUser = userRepository.findByUsername(user.getUsername());

        if (existingUser.isPresent() && existingUser.get().getPassword().equals(user.getPassword())) {
            return JwtUtil.generateToken(user.getUsername(), existingUser.get().getRoles());
        } else {
            throw new RuntimeException("Invalid username or password");
        }
    }
}
