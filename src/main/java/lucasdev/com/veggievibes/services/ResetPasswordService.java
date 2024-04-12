package lucasdev.com.veggievibes.services;

import lucasdev.com.veggievibes.domain.reset_password.ResetPassword;
import lucasdev.com.veggievibes.domain.reset_password.exceptions.InvalidTokenTimeException;
import lucasdev.com.veggievibes.domain.reset_password.exceptions.ResetPasswordNotFoundException;
import lucasdev.com.veggievibes.domain.user.User;
import lucasdev.com.veggievibes.domain.user.exceptions.UserNotFoundException;
import lucasdev.com.veggievibes.dto.reset_password.ResetPasswordRequestDTO;
import lucasdev.com.veggievibes.dto.reset_password.ResetPasswordTokenDTO;
import lucasdev.com.veggievibes.dto.reset_password.ResetPasswordValidateDTO;
import lucasdev.com.veggievibes.repositories.ResetPasswordRepository;
import lucasdev.com.veggievibes.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;

@Service
public class ResetPasswordService {

    @Autowired
    private ResetPasswordRepository resetPasswordRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public ResetPasswordTokenDTO generate(ResetPasswordRequestDTO resetPasswordRequestDTO) {
        Optional<User> userExists = this.userRepository.findByEmail(resetPasswordRequestDTO.email());

        if(userExists.isEmpty()) throw new UserNotFoundException("User not found");

        Optional<ResetPassword> resetPasswordExists = this.resetPasswordRepository.findByUserId(userExists.get().getId());

        if(resetPasswordExists.isPresent()) {
            this.resetPasswordRepository.delete(resetPasswordExists.get());
        }

        ResetPassword resetPassword = new ResetPassword();
        resetPassword.setUser(userExists.get());
        resetPassword.setCreatedAt(LocalDateTime.now());
        resetPassword.setUpdatedAt(LocalDateTime.now());

        this.resetPasswordRepository.save(resetPassword);

        this.emailService.sendEmail(
                resetPasswordRequestDTO.email(),
                "Veggie Vibes - Reset Password",
                "Follow the link to reset your password http://localhost:3000/reset-password/" + resetPassword.getToken()
        );

        return new ResetPasswordTokenDTO(resetPassword.getToken());
    }

    public ResetPasswordValidateDTO validate(String token) {
        Optional<ResetPassword> resetPasswordExists = this.resetPasswordRepository.findByToken(token);

        if(resetPasswordExists.isEmpty()) throw new ResetPasswordNotFoundException("Token not found");

        Instant expirationInstant = Instant.ofEpochSecond(resetPasswordExists.get().getExpiresAt());

        LocalDateTime expirationTime = LocalDateTime.ofInstant(expirationInstant, ZoneId.of("America/Sao_Paulo"));

        LocalDateTime now = LocalDateTime.now();

        if(now.isAfter(expirationTime)) throw new InvalidTokenTimeException("Token is expired");

        return new ResetPasswordValidateDTO(true);
    }

    public void reset() {}

}
