package lucasdev.com.veggievibes.domain.user.exceptions;

import lucasdev.com.veggievibes.domain.user.User;
import lucasdev.com.veggievibes.dto.user.UserRequestDTO;
import lucasdev.com.veggievibes.repositories.UserRepository;
import lucasdev.com.veggievibes.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;


import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class InvalidRoleExceptionTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    User user;

    UserRequestDTO userRequestDTO;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);

        this.user = new User();
        this.user.setName("Lucas");
        this.user.setEmail("lucas@gmail.com");
        this.user.setPassword("12345678");
        this.user.setRole("TESTE");

        this.userRequestDTO = new UserRequestDTO(this.user.getName(), this.user.getEmail(), this.user.getPassword(), this.user.getPassword(), this.user.getRole());
    }

    @Test
    void shouldNotBeAbleToCreateAUserIfRoleIsInvalid() {
        assertThrows(InvalidRoleException.class, () -> {
            userService.create(this.userRequestDTO);
        });
    }
}
