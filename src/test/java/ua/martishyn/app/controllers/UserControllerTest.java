package ua.martishyn.app.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import ua.martishyn.app.models.UserDTO;
import ua.martishyn.app.service.UserService;

import java.util.List;

import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    private List<UserDTO> userDTOList;

    @Test
    @WithUserDetails("andrey95sevas@gmail.com")
    void shouldReturnUsersPageWhenAccessingUsersPage() throws Exception {
        this.mockMvc.perform(get("/admin/users"))
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(status().isOk());

    }
}
