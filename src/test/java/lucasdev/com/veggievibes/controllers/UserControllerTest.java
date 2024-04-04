package lucasdev.com.veggievibes.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import lucasdev.com.veggievibes.domain.user.User;
import lucasdev.com.veggievibes.dto.user.UserIdDTO;
import lucasdev.com.veggievibes.dto.user.UserRequestDTO;
import lucasdev.com.veggievibes.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    User user;

    UserRequestDTO userRequestDTO;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(userController).alwaysDo(print()).build();

        this.user = new User();
        this.user.setName("Lucas");
        this.user.setEmail("lucas@gmail.com");
        this.user.setPassword("12345678");
        this.user.setRole("ADMIN");

        this.userRequestDTO = new UserRequestDTO(this.user.getName(), this.user.getEmail(), this.user.getPassword(), this.user.getPassword(), this.user.getRole());

        this.objectMapper = new ObjectMapper();
    }

    @Test
    void UserController_CreateUser_Return201() throws Exception {
        when(userService.create(userRequestDTO)).thenReturn(new UserIdDTO("1"));

        this.mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(userRequestDTO))
        ).andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn();
    }
}
