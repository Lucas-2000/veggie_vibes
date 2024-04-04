package lucasdev.com.veggievibes.domain.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@ExtendWith(MockitoExtension.class)
public class UserTest {

    @InjectMocks
    User user;

    @BeforeEach
    public void setup() {
        this.user = new User();
        this.user.setId("1");
        this.user.setName("Lucas");
        this.user.setEmail("lucas@gmail.com");
        this.user.setPassword("12345678");
        this.user.setEmailValidated(false);
        this.user.setRole("ADMIN");
        this.user.setCreatedAt(LocalDateTime.now());
        this.user.setUpdatedAt(LocalDateTime.now());
    }

    @Test
    void testId() {
        assertEquals("1", user.getId());
    }


    @Test
    void testName() {
        assertEquals("Lucas", user.getName());
    }

    @Test
    void testEmail() {
        assertEquals("lucas@gmail.com", user.getEmail());
    }

    @Test
    void testPassword() {
        assertEquals("12345678", user.getPassword());
    }

    @Test
    void testEmailValidated() {
        assertFalse(user.isEmailValidated());
    }

    @Test
    void testRole() {
        assertEquals("ADMIN", user.getRole());
    }

    @Test
    void testCreatedAt() {
        assertEquals(LocalDateTime.now().getDayOfYear(), user.getCreatedAt().getDayOfYear());
    }

    @Test
    void testUpdatedAt() {
        assertEquals(LocalDateTime.now().getDayOfYear(), user.getUpdatedAt().getDayOfYear());
    }
}
