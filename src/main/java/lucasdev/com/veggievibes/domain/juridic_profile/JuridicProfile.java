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

    @Column(name = "legal_name", nullable = false)
    private String legalName;

    @Column(nullable = false, unique = true)
    private String cnpj;

    @Column(name = "state_registration")
    private String stateRegistration;

    @Column(name = "trade_name")
    private String tradeName;

    @Column(name = "activity_sector")
    private String activitySector;

    @Column(name = "commercial_address")
    private String commercialAddress;

    @Column(name = "commercial_phone")
    private String commercialPhone;

    @Column()
    private String website;

    @Column(name = "commercial_email")
    private String commercialEmail;

    @Column(name = "company_description")
    private String companyDescription;

    @Column(name = "company_logo")
    private String companyLogo;

    @Column(name = "legal_representative_contact_name")
    private String legalRepresentativeContactName;

    @Column(name = "legal_representative_contact_email")
    private String legalRepresentativeContactEmail;

    @Column(name = "legal_representative_contact_phone")
    private String legalRepresentativeContactPhone;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
