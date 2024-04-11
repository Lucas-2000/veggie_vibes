package lucasdev.com.veggievibes.repositories;

import lucasdev.com.veggievibes.domain.juridic_profile.JuridicProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JuridicProfileRepository extends JpaRepository<JuridicProfile, String> {
}
