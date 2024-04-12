package lucasdev.com.veggievibes.repositories;

import lucasdev.com.veggievibes.domain.reset_password.ResetPassword;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ResetPasswordRepository extends JpaRepository<ResetPassword, String> {
    Optional<ResetPassword> findByUserId(String userId);
}
