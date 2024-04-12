package lucasdev.com.veggievibes.services;

import lucasdev.com.veggievibes.domain.reset_password.ResetPassword;
import lucasdev.com.veggievibes.domain.user.User;
import lucasdev.com.veggievibes.domain.user.exceptions.UserNotFoundException;
import lucasdev.com.veggievibes.dto.reset_password.ResetPasswordRequest;
import lucasdev.com.veggievibes.dto.reset_password.ResetPasswordTokenDTO;
import lucasdev.com.veggievibes.repositories.ResetPasswordRepository;
import lucasdev.com.veggievibes.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
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
    public ResetPasswordTokenDTO generate(ResetPasswordRequest resetPasswordRequest) {
        Optional<User> userExists = this.userRepository.findByEmail(resetPasswordRequest.email());

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
                resetPasswordRequest.email(),
                "Veggie Vibes - Reset Password",
                "Follow the link to reset your password http://localhost:3000/reset-password/" + resetPassword.getToken()
        );

        return new ResetPasswordTokenDTO(resetPassword.getToken());
    }

    public void reset() {}

}
