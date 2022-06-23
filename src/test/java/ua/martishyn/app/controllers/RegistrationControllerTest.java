package ua.martishyn.app.controllers;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ua.martishyn.app.controllers.security.RegistrationController;
import ua.martishyn.app.service.UserService;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


class RegistrationControllerTest {
    private static MockMvc mockMvc;
    private static UserService userService;


    @BeforeAll
    static void setup() {
       mockMvc = MockMvcBuilders.standaloneSetup(new RegistrationController(userService)).build();
    }

    @Test
    void shouldRedirectToRegisterPageWhenPasswordsAreNotMatching() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/register")
                        .param("firstName", "Test")
                        .param("lastName", "Test")
                        .param("email", "test@email.com")
                        .param("password", "passwordTest1")
                        .param("verifyPassword", "passwordTest12"))
                .andExpect(model().errorCount(1))
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("registration"));
    }
}

