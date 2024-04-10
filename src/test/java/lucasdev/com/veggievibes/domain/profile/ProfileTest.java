package lucasdev.com.veggievibes.domain.profile;

import lucasdev.com.veggievibes.domain.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
public class ProfileTest {

    @InjectMocks
    Profile profile;

    @Mock
    private User user;

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

        this.profile = new Profile();
        this.profile.setId("1");
        this.profile.setUser(user);
        this.profile.setCpf("11111111111");
        this.profile.setFirstName("Lucas");
        this.profile.setLastName("Marchesoni");
        this.profile.setPhoneNumber("11911111111");
        this.profile.setAddress("Street 1");
        this.profile.setCity("City");
        this.profile.setState("SP");
        this.profile.setPostalCode("00000000");
        this.profile.setCreatedAt(LocalDateTime.now());
        this.profile.setUpdatedAt(LocalDateTime.now());
    }


    @Test
    public void testProfileCreation() {
        assertNotNull(profile);
    }

    @Test
    public void testId() {
        assertEquals("1", profile.getId());
    }

    @Test
    public void testUser() {
        assertEquals(user, profile.getUser());
    }

    @Test
    public void testCpf() {
        assertEquals("11111111111", profile.getCpf());
    }

    @Test
    public void testFirstName() {
        assertEquals("Lucas", profile.getFirstName());
    }

    @Test
    public void testLastName() {
        assertEquals("Marchesoni", profile.getLastName());
    }

    @Test
    public void testPhoneNumber() {
        assertEquals("11911111111", profile.getPhoneNumber());
    }

    @Test
    public void testAddress() {
        assertEquals("Street 1", profile.getAddress());
    }

    @Test
    public void testCity() {
        assertEquals("City", profile.getCity());
    }

    @Test
    public void testState() {
        assertEquals("SP", profile.getState());
    }

    @Test
    public void testPostalCode() {
        assertEquals("00000000", profile.getPostalCode());
    }

    @Test
    public void testCreatedAt() {
        assertEquals(LocalDateTime.now().getDayOfYear(), profile.getCreatedAt().getDayOfYear());
    }

    @Test
    public void testUpdatedAt() {
        assertEquals(LocalDateTime.now().getDayOfYear(), profile.getUpdatedAt().getDayOfYear());
    }
}
