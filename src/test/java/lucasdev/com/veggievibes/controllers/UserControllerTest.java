package lucasdev.com.veggievibes.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import lucasdev.com.veggievibes.domain.user.User;
import lucasdev.com.veggievibes.domain.user.exceptions.EmailAlreadyExistsException;
import lucasdev.com.veggievibes.dto.user.*;
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

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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

    UserResponseDTO userResponseDTO;

    UserUpdateDTO userUpdateDTO;

    UserMessageDTO userMessageDTO;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(userController).alwaysDo(print()).build();

        this.user = new User();
        this.user.setId("1");
        this.user.setName("Lucas");
        this.user.setEmail("lucas@gmail.com");
        this.user.setPassword("12345678");
        this.user.setEmailValidated(false);
        this.user.setRole("ADMIN");

        this.userRequestDTO = new UserRequestDTO(this.user.getName(), this.user.getEmail(), this.user.getPassword(), this.user.getPassword(), this.user.getRole());

        this.objectMapper = new ObjectMapper();

        this.userResponseDTO = new UserResponseDTO(this.user.getId(), this.user.getName(), this.user.getEmail(), this.user.isEmailValidated(), this.user.getRole());

        this.userUpdateDTO = new UserUpdateDTO(this.user.getName(),this.user.getPassword(), this.user.getPassword());

        this.userMessageDTO = new UserMessageDTO(null);
    }

    @Test
    void UserController_CreateUser_Return201() throws Exception {
        when(this.userService.create(userRequestDTO)).thenReturn(new UserIdDTO("1"));

        this.mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(userRequestDTO))
        ).andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn();
    }


    @Test
    void UserController_FindUserById_Return200() throws Exception {
        when(this.userService.findUserById("1")).thenReturn(this.userResponseDTO);

        this.mockMvc.perform(get("/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(this.userResponseDTO))
                ).andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
    }

    @Test
    void UserController_UpdateUser_Return200() throws Exception {
        when(this.userService.update("1", userUpdateDTO)).thenReturn(new UserIdDTO("1"));

        this.mockMvc.perform(patch("/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(userUpdateDTO))
                ).andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
    }

    @Test
    void UserController_DeleteUser_Return200() throws Exception {
        doNothing().when(userService).delete("1");

        this.mockMvc.perform(delete("/users/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
