package ua.martishyn.app.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.martishyn.app.entities.User;
import ua.martishyn.app.models.UserDTO;
import ua.martishyn.app.models.UserRegisterDTO;
import ua.martishyn.app.repositories.UserRepository;
import ua.martishyn.app.utils.enums.Role;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;
    private final EmailService emailService;

    @Autowired
    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       ModelMapper modelMapper,
                       EmailService emailService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
        this.emailService = emailService;
    }

    @Transactional
    public boolean registerUser(UserRegisterDTO userRegisterDTO) {
        if (checkIfUserExist(userRegisterDTO.getEmail())) {
            return false;
        }
        User user = User.builder()
                .firstName(userRegisterDTO.getFirstName())
                .lastName(userRegisterDTO.getLastName())
                .email(userRegisterDTO.getEmail())
                .password(passwordEncoder.encode(userRegisterDTO.getPassword()))
                .role(Role.CUSTOMER)
                .build();
        userRepository.save(user);
        return emailService.sendWelcomeLetter(user);
    }

    @Transactional(readOnly = true)
    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public UserDTO getUserDtoByEntityId(Integer id) {
        Optional<User> userFromDb = userRepository.findUserById(id);
        return userFromDb.map(this::convertToDto).orElse(null);
    }

    @Transactional
    public boolean updateUserFromDtoData(Integer id, UserDTO userDTO) {
        Optional<User> userById = userRepository.findUserById(id);
        if (!userById.isPresent()) {
            return false;
        }
        User mappedEntity = convertToEntity(userDTO);
        mappedEntity.setPassword(userById.get().getPassword());
        userRepository.save(mappedEntity);
        return true;
    }

    @Transactional
    public boolean deleteUserById(Integer userId) {
        Optional<User> userById = userRepository.findUserById(userId);
        if (!userById.isPresent()) {
            return false;
        }
        userRepository.delete(userById.get());
        return true;
    }

    public UserDTO convertToDto(User user) {
        return modelMapper.map(user, UserDTO.class);
    }

    public User convertToEntity(UserDTO userDTO) {
        return modelMapper.map(userDTO, User.class);
    }

    public boolean checkIfUserExist(String email) {
        return userRepository.findUserByEmail(email).isPresent();
    }
}
