package lucasdev.com.veggievibes.domain.user.exceptions;

import lucasdev.com.veggievibes.domain.user.User;
import lucasdev.com.veggievibes.dto.user.LoginRequestDTO;
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
public class IncorrectLoginExceptionTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    User user;


    LoginRequestDTO loginRequestDTO;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);

        this.user = new User();
        this.user.setEmail("lucas@gmail.com");
        this.user.setPassword("12345678");

        this.loginRequestDTO = new LoginRequestDTO(this.user.getEmail(), this.user.getPassword());
    }

    @Test
    void shouldNotBeAbleToAuthAnUserIfEmaiOrPasswordAreIncorrect() {
        this.loginRequestDTO = new LoginRequestDTO("teste", "1234");

        assertThrows(IncorrectLoginException.class, () -> {
            this.userService.login(this.loginRequestDTO);
        });
    }
}
