package ua.martishyn.app.service;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.martishyn.app.models.UserRegisterDTO;
import ua.martishyn.app.entities.User;
import ua.martishyn.app.repositories.UserRepository;
import ua.martishyn.app.utils.Role;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    @SneakyThrows
    public void register(UserRegisterDTO userRegisterDTO)  {
        if (checkIfUserExist(userRegisterDTO.getEmail())){
            throw new Exception("User already exists for this email");
        }
        User user = User.builder()
                .firstName(userRegisterDTO.getFirstName())
                .lastName(userRegisterDTO.getLastName())
                .email(userRegisterDTO.getEmail())
                .password(passwordEncoder.encode(userRegisterDTO.getPassword()))
                .role(Role.CUSTOMER)
                .build();
        userRepository.save(user);
    }


    public boolean checkIfUserExist(String email) {
        return userRepository.findUserByEmail(email).isPresent();
    }
}
