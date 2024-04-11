package lucasdev.com.veggievibes.domain.juridic_profile;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lucasdev.com.veggievibes.domain.user.User;

import java.time.LocalDateTime;

@Entity
@Table(name="juridic_profiles")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JuridicProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(nullable = false)
    private String id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String legal_name;

    @Column(nullable = false, length = 18, unique = true)
    private String cnpj;

    @Column()
    private String state_registration;

    @Column()
    private String trade_name;

    @Column()
    private String activity_sector;

    @Column()
    private String commercial_address;

    @Column()
    private String commercial_phone;

    @Column()
    private String website;

    @Column()
    private String commercial_email;

    @Column()
    private String company_description;

    @Column()
    private String company_logo;

    @Column()
    private String legal_representative_contact_name;

    @Column()
    private String legal_representative_contact_email;

    @Column()
    private String legal_representative_contact_phone;

    @Column(nullable = false)
    private LocalDateTime created_at;

    @Column(nullable = false)
    private LocalDateTime updated_at;
}
