package lucasdev.com.veggievibes.domain.reset_password;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lucasdev.com.veggievibes.domain.user.User;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Entity
@Table(name = "reset_password")
@Getter
@Setter
@AllArgsConstructor
public class ResetPassword {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(nullable = false)
    private String id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String token;

    @Column(name = "expires_at", nullable = false)
    private Integer expiresAt;

    @Column(name="created_at")
    private LocalDateTime createdAt;

    @Column(name="updated_at")
    private LocalDateTime updatedAt;

    public ResetPassword() {
        this.token = generateToken();
        this.expiresAt = getExpiresAt();
    }

    private static String generateToken() {
        UUID token = UUID.randomUUID();

        return token.toString();
    }

    private static Integer getExpiresAt() {
        LocalDateTime now = LocalDateTime.now();

        LocalDateTime expires = now.plusHours(1);

        System.out.println(expires);

        long expiresEpochSeconds = expires.atZone(ZoneId.of("America/Sao_Paulo")).toInstant().getEpochSecond();

        return (int)expiresEpochSeconds;
    }

}
