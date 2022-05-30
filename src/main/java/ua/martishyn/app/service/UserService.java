package ua.martishyn.app.service;

import lombok.SneakyThrows;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.martishyn.app.entities.User;
import ua.martishyn.app.models.UserDTO;
import ua.martishyn.app.models.UserRegisterDTO;
import ua.martishyn.app.repositories.UserRepository;
import ua.martishyn.app.utils.Role;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    @Autowired
    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
    }

    public Authentication getCurrentUser() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    @Transactional
    @SneakyThrows
    public void  registerUser(UserRegisterDTO userRegisterDTO) {
        if (checkIfUserExist(userRegisterDTO.getEmail())) {
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


    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public UserDTO getUserDtoByEntityId(int id) throws Exception {
        Optional<User> userFromDb = userRepository.findUserById(id);
        if (!userFromDb.isPresent()) {
            throw new Exception("User not found with such id");
        }
        return convertToDto(userFromDb.get());
    }

    public void updateUserFromDtoData(int id, UserDTO userDTO) throws Exception {
        final Optional<User> userById = userRepository.findUserById(id);
        if (!userById.isPresent()) {
            throw new Exception("User not found with such id");
        }
        User mappedEntity = convertToEntity(userDTO);
        mappedEntity.setPassword(userById.get().getPassword());
        userRepository.save(mappedEntity);
    }

    public void deleteUserById(int userId) throws Exception {
        Optional<User> userById = userRepository.findById(userId);
        if (!userById.isPresent()) {
            throw new Exception("No such user in DB");
        }
        User deleteUser = userById.get();
        userRepository.delete(deleteUser);
    }

    private UserDTO convertToDto(User user) {
        return modelMapper.map(user, UserDTO.class);
    }

    private User convertToEntity(UserDTO userDTO) {
        return modelMapper.map(userDTO, User.class);
    }

    public boolean checkIfUserExist(String email) {
        return userRepository.findUserByEmail(email).isPresent();
    }


}
