package ua.martishyn.app.unit_testing;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import ua.martishyn.app.entities.User;
import ua.martishyn.app.models.UserDTO;
import ua.martishyn.app.models.UserRegisterDTO;
import ua.martishyn.app.repositories.UserRepository;
import ua.martishyn.app.service.EmailService;
import ua.martishyn.app.service.UserService;

import java.util.Collections;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Spy
    private ModelMapper modelMapper;

    @Spy
    private PasswordEncoder passwordEncoder;

    @Mock
    private EmailService emailService;

    @InjectMocks
    private UserService userService;
    private User user;


    @BeforeEach
    void initServiceClass() {
        userService = new UserService(userRepository, passwordEncoder, modelMapper, emailService);
        user = User.builder()
                .id(1)
                .password("password")
                .build();

    }

    @Test
    void shouldReturnListOfDTOUsersWhenFindingAll() {
        when(userRepository.findAll()).thenReturn(Collections.emptyList());
        Assertions.assertEquals(0, userService.getAllUsers().size());
        Assertions.assertNotNull(userService.getAllUsers());
    }

    @Test
    void shouldReturnTrueIfEmailIsNotExistInDatabaseWhenRegisteringUser() {
        UserRegisterDTO userRegisterDTO = UserRegisterDTO.builder()
                .firstName("test")
                .lastName("test")
                .email("hey")
                .password("password1")
                .build();
        when(userRepository.findUserByEmail(anyString())).thenReturn(Optional.empty());
        when(emailService.sendWelcomeLetter(any(User.class))).thenReturn(true);
        Assertions.assertTrue(userService.registerUser(userRegisterDTO));
    }

    @Test
    void shouldReturnFalseIfEmailExistInDatabaseWhenRegisteringUser() {
        UserRegisterDTO userRegisterDTO = UserRegisterDTO.builder().
                email("hey")
                .build();
        when(userRepository.findUserByEmail(userRegisterDTO.getEmail())).thenReturn(Optional.of(user));
        Assertions.assertFalse(userService.registerUser(userRegisterDTO));
    }

    @Test
    void shouldReturnFalseWhenUserOptionalIsEmptyWhenFindingById() {
        when(userRepository.findUserByEmail(anyString())).thenReturn(Optional.empty());
        Assertions.assertFalse(userService.checkIfUserExist(anyString()));
    }

    @Test
    void shouldReturnNullWhenUserNotFoundById() {
        when(userRepository.findUserById(anyInt())).thenReturn(Optional.empty());
        Assertions.assertNull(userService.getUserDtoByEntityId(anyInt()));
    }

    @Test
    void shouldReturnConvertedEntityToUserDTOWhenUserFoundById() {
        when(userRepository.findUserById(anyInt())).thenReturn(Optional.of(user));
        Assertions.assertNotNull(userService.getUserDtoByEntityId(anyInt()));
    }

    @Test
    void shouldReturnFalseIfUserIsEmptyWhenUpdatingFromDTOData() {
        UserDTO userDTO = userService.convertToDto(user);
        when(userRepository.findUserById(anyInt())).thenReturn(Optional.empty());
        Assertions.assertFalse(userService.updateUserFromDtoData(anyInt(), userDTO));
    }

    @Test
    void shouldReturnTrueIfUserIsPresentWhenUpdatingFromDTOData() {
        UserDTO userDTO = userService.convertToDto(user);
        when(userRepository.findUserById(anyInt())).thenReturn(Optional.of(user));
        when(userRepository.save(user)).thenReturn(user);
        Assertions.assertTrue(userService.updateUserFromDtoData(anyInt(), userDTO));
    }

    @Test
    void shouldReturnFalseIfUserIsNotPresentWhenDeletingById() {
        when(userRepository.findUserById(anyInt())).thenReturn(Optional.empty());
        Assertions.assertFalse(userService.deleteUserById(anyInt()));
    }

    @Test
    void shouldReturnTrueIfUserIsPresentWhenDeletingById() {
        when(userRepository.findUserById(anyInt())).thenReturn(Optional.of(user));
        Assertions.assertTrue(userService.deleteUserById(anyInt()));
    }

}
