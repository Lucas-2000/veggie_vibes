package lucasdev.com.veggievibes.services;

import lucasdev.com.veggievibes.services.EmailService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

public class EmailServiceTest {

    @Mock
    private JavaMailSender javaMailSender;

    @InjectMocks
    private EmailService emailService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSendEmail() {
        String toEmail = "lucas@gmail.com";
        String subject = "Subject test";
        String body = "Body test";

        emailService.sendEmail(toEmail, subject, body);

        verify(javaMailSender).send(any(SimpleMailMessage.class));
    }
}