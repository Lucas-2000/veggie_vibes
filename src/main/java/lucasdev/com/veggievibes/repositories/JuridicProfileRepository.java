package lucasdev.com.veggievibes.repositories;

import lucasdev.com.veggievibes.domain.juridic_profile.JuridicProfile;
import lucasdev.com.veggievibes.domain.profile.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JuridicProfileRepository extends JpaRepository<JuridicProfile, String> {
    Optional<JuridicProfile> findByUserId(String userId);

    Optional<Profile> findByCnpj(String cnpj);
}
