package lucasdev.com.veggievibes.services;

import lucasdev.com.veggievibes.domain.user.User;
import lucasdev.com.veggievibes.domain.user.exceptions.ArePasswordAndRePasswordNotEqualException;
import lucasdev.com.veggievibes.domain.user.exceptions.EmailAlreadyExistsException;
import lucasdev.com.veggievibes.domain.user.exceptions.InvalidRoleException;
import lucasdev.com.veggievibes.domain.user.exceptions.PasswordLengthException;
import lucasdev.com.veggievibes.dto.user.UserRequestDTO;
import lucasdev.com.veggievibes.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

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
        this.user.setRole("ADMIN");

        this.userRequestDTO = new UserRequestDTO(this.user.getName(), this.user.getEmail(), this.user.getPassword(), this.user.getPassword(), this.user.getRole());
    }

    @Test
    void shouldBeAbleToCreateAnUser() {
        when(userRepository.findByEmail(this.user.getEmail())).thenReturn(Optional.empty());

        this.userService.create(this.userRequestDTO);

        verify(userRepository, times(1)).save(any());
    }

    @Test
    void shouldThrowEmailAlreadyExistsException() {
        when(userRepository.findByEmail("lucas@gmail.com")).thenReturn(Optional.of(new User()));

        assertThrows(EmailAlreadyExistsException.class, () -> {
            userService.create(this.userRequestDTO);
        });
    }

    @Test
    void shouldThrowInvalidRoleException() {
        this.user.setRole("TESTE");
        this.userRequestDTO = new UserRequestDTO(this.user.getName(), this.user.getEmail(), this.user.getPassword(), this.user.getPassword(), this.user.getRole());

        assertThrows(InvalidRoleException.class, () -> {
            userService.create(this.userRequestDTO);
        });
    }

    @Test
    void shouldThrowArePasswordAndRePasswordNotEqualException() {
        this.userRequestDTO = new UserRequestDTO(this.user.getName(), this.user.getEmail(), this.user.getPassword(), "123456789", this.user.getRole());

        assertThrows(ArePasswordAndRePasswordNotEqualException.class, () -> {
            userService.create(this.userRequestDTO);
        });
    }

    @Test
    void shouldThrowPasswordLengthException() {
        this.userRequestDTO = new UserRequestDTO(this.user.getName(), this.user.getEmail(), "1234567", "1234567", this.user.getRole());

        assertThrows(PasswordLengthException.class, () -> {
            userService.create(this.userRequestDTO);
        });
    }
}
