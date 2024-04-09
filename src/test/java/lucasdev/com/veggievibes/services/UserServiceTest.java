package lucasdev.com.veggievibes.services;

import lucasdev.com.veggievibes.domain.user.User;
import lucasdev.com.veggievibes.domain.user.exceptions.*;
import lucasdev.com.veggievibes.dto.user.*;
import lucasdev.com.veggievibes.infra.security.TokenService;
import lucasdev.com.veggievibes.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mindrot.jbcrypt.BCrypt;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Mock
    private EmailService emailService;

    @Mock
    private TokenService tokenService;

    User user;

    UserRequestDTO userRequestDTO;

    UserUpdateDTO userUpdateDTO;

    LoginRequestDTO loginRequestDTO;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);

        this.user = new User();
        this.user.setId("1");
        this.user.setName("Lucas");
        this.user.setEmail("lucas@gmail.com");
        this.user.setPassword(BCrypt.hashpw("12345678", BCrypt.gensalt()));
        this.user.setRole("ADMIN");

        this.userRequestDTO = new UserRequestDTO(this.user.getName(), this.user.getEmail(), this.user.getPassword(), this.user.getPassword(), this.user.getRole());

        this.userUpdateDTO = new UserUpdateDTO(this.user.getName(), this.user.getPassword(), this.user.getPassword());

        this.loginRequestDTO = new LoginRequestDTO("lucas@gmail.com", "12345678");
    }

    @Test
    void shouldBeAbleToCreateAnUser() {
        when(userRepository.findByEmail(this.user.getEmail())).thenReturn(Optional.empty());

        this.userService.create(this.userRequestDTO);

        verify(userRepository, times(1)).save(any());
        verify(emailService, times(1)).sendEmail(
                eq(this.userRequestDTO.email()),
                eq("Validate Email"),
                anyString()
        );
    }

    @Test
    void shouldThrowEmailAlreadyExistsException() {
        when(userRepository.findByEmail("lucas@gmail.com")).thenReturn(Optional.of(new User()));

        assertThrows(EmailAlreadyExistsException.class, () -> {
            this.userService.create(this.userRequestDTO);
        });
    }

    @Test
    void shouldThrowInvalidRoleException() {
        this.user.setRole("TESTE");
        this.userRequestDTO = new UserRequestDTO(this.user.getName(), this.user.getEmail(), this.user.getPassword(), this.user.getPassword(), this.user.getRole());

        assertThrows(InvalidRoleException.class, () -> {
            this.userService.create(this.userRequestDTO);
        });
    }

    @Test
    void shouldThrowArePasswordAndRePasswordNotEqualException() {
        this.userRequestDTO = new UserRequestDTO(this.user.getName(), this.user.getEmail(), this.user.getPassword(), "123456789", this.user.getRole());

        assertThrows(ArePasswordAndRePasswordNotEqualException.class, () -> {
            this.userService.create(this.userRequestDTO);
        });
    }

    @Test
    void shouldThrowPasswordLengthException() {
        this.userRequestDTO = new UserRequestDTO(this.user.getName(), this.user.getEmail(), "1234567", "1234567", this.user.getRole());

        assertThrows(PasswordLengthException.class, () -> {
            this.userService.create(this.userRequestDTO);
        });
    }

    @Test
    void shouldBeAbleToFindUserById() {
        when(this.userRepository.findById("1")).thenReturn(Optional.of(user));

        var result = userService.findUserById("1");

        assertEquals(user.getId(), result.id());
    }

    @Test
    void shouldThrowUserNotFoundException() {
        assertThrows(UserNotFoundException.class, () -> {
            this.userService.findUserById("teste");
        });
    }

    @Test
    void shouldBeAbleToUpdateUser() {
        when(this.userRepository.findById("1")).thenReturn(Optional.of(user));

        var result = userService.update("1", this.userUpdateDTO);

        verify(userRepository).save(any(User.class));

        assertEquals(user.getId(), result.userId());

        assertEquals(userUpdateDTO.name(), user.getName());
    }

    @Test
    void shouldBeAbleToDeleteUser() {
        when(this.userRepository.findById("1")).thenReturn(Optional.of(user));

        this.userService.delete("1");

        verify(userRepository).delete(user);
    }


    @Test
    void shouldBeAbleToLoginUser() {
        when(userRepository.findByEmail("lucas@gmail.com")).thenReturn(Optional.of(user));
        when(tokenService.generateToken(any(User.class))).thenReturn("mockedToken");

        LoginResponseDTO loginResponseDTO = userService.login(this.loginRequestDTO);

        assertNotNull(loginResponseDTO.token());
    }

    @Test
    void shouldThrowIncorrectLoginException() {
        this.loginRequestDTO = new LoginRequestDTO("teste", "12345678");

        assertThrows(IncorrectLoginException.class, () -> {
            this.userService.login(this.loginRequestDTO);
        });
    }
}
