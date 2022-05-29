package ua.martishyn.app.utils;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.event.annotation.BeforeTestClass;
import org.springframework.test.context.event.annotation.BeforeTestMethod;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ua.martishyn.app.controllers.user.RegistrationController;
import ua.martishyn.app.service.UserService;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


class ClassValidationMvcTest {
    private static MockMvc mockMvc;
    private static UserService userService;


    @BeforeAll
    static void setup() {
       mockMvc = MockMvcBuilders.standaloneSetup(new RegistrationController(userService)).build();
    }

    @Test
    void shouldNotSendPostWhenPasswordsAreNotMatching() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/register")
                        .param("firstName", "Test")
                        .param("lastName", "Test")
                        .param("email", "test@email.com")
                        .param("password", "passwordTest1")
                        .param("confirmedPassword", "passwordTest12"))
                .andExpect(model().errorCount(1))
                .andExpect(status().isOk());
    }
}

