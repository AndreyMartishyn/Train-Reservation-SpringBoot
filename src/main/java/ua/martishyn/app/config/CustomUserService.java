package ua.martishyn.app.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ua.martishyn.app.entities.User;
import ua.martishyn.app.models.UserPrincipal;
import ua.martishyn.app.repositories.UserRepository;

import java.util.Optional;

@Service
public class CustomUserService implements UserDetailsService {
    private final UserRepository userRepository;

    @Autowired
    public CustomUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Optional<User> userFromDb = userRepository.findUserByEmail(s);
        if (!userFromDb.isPresent()) {
            throw new UsernameNotFoundException("User not found");
        }
        return new UserPrincipal(userFromDb.get());
    }
}
