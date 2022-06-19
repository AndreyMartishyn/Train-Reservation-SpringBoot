package ua.martishyn.app.security;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest
class LoginSecurityTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldRedirectToLoginPageWhenNotAuthorized() throws Exception {
        this.mockMvc.perform(get("/admin/users"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/login"));
    }

    @Test
    void shouldRedirectToMainWhenLoginAuthorized() throws Exception {
        this.mockMvc.perform(formLogin()
                        .user("andrey95sevas@gmail.com")
                        .password("Password1"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/index"));
    }

    @Test
    void shouldRedirectToLoginPageWithBadCredentials() throws Exception {
        this.mockMvc.perform(post("http://localhost/login")
                        .param("andrey95sevas@gmail.com", "passwordWrong"))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @WithUserDetails("andrey95sevas@gmail.com")
    void shouldAccessAdminResourceWhenAuthorityIsAdmin() throws Exception {
        this.mockMvc.perform(get("/admin/stations"))
                .andDo(print())
                .andExpect(authenticated());
    }
}
