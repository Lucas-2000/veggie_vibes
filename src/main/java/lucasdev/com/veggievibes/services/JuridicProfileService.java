package lucasdev.com.veggievibes.services;

import lucasdev.com.veggievibes.domain.juridic_profile.JuridicProfile;
import lucasdev.com.veggievibes.domain.juridic_profile.exceptions.CNPJAlreadyExistsException;
import lucasdev.com.veggievibes.domain.juridic_profile.exceptions.InvalidCNPJFormatException;
import lucasdev.com.veggievibes.domain.juridic_profile.exceptions.JuridicProfileAlreadyExistsException;
import lucasdev.com.veggievibes.domain.user.User;
import lucasdev.com.veggievibes.domain.user.exceptions.InvalidRoleException;
import lucasdev.com.veggievibes.domain.user.exceptions.UserNotFoundException;
import lucasdev.com.veggievibes.dto.juridic_profile.JuridicProfileIdDTO;
import lucasdev.com.veggievibes.dto.juridic_profile.JuridicProfileRequestDTO;
import lucasdev.com.veggievibes.repositories.JuridicProfileRepository;
import lucasdev.com.veggievibes.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class JuridicProfileService {

    @Autowired
    private JuridicProfileRepository juridicProfileRepository;

    @Autowired
    private UserRepository userRepository;

    private final String REGEX_CNPJ = "^\\d{2}\\.\\d{3}\\.\\d{3}\\/\\d{4}-\\d{2}$";

    public JuridicProfileIdDTO create(JuridicProfileRequestDTO juridicProfileRequestDTO){
        Optional<User> userExists = this.userRepository.findById(juridicProfileRequestDTO.userId());

        if(userExists.isEmpty()) throw new UserNotFoundException("User not found");

        Optional<JuridicProfile> juridicProfileExists = this.juridicProfileRepository.findByUserId(juridicProfileRequestDTO.userId());

        if(juridicProfileExists.isPresent()) throw new JuridicProfileAlreadyExistsException("User already have profile");

        if(!userExists.get().getRole().equals("SELLER")) throw new InvalidRoleException("Invalid user role, needs to be SELLER");

        if(!juridicProfileRequestDTO.cnpj().matches(REGEX_CNPJ)) throw new InvalidCNPJFormatException("Invalid CNPJ format, the correct is XX.XXX.XXX/XXXX-XX");

        JuridicProfile juridicProfile = new JuridicProfile();
        juridicProfile.setUser(userExists.get());
        juridicProfile.setLegalName(juridicProfileRequestDTO.legalName());
        juridicProfile.setCnpj(juridicProfileRequestDTO.cnpj());
        juridicProfile.setStateRegistration(juridicProfileRequestDTO.stateRegistration());
        juridicProfile.setTradeName(juridicProfileRequestDTO.tradeName());
        juridicProfile.setActivitySector(juridicProfileRequestDTO.activitySector());
        juridicProfile.setCommercialAddress(juridicProfileRequestDTO.commercialAddress());
        juridicProfile.setCommercialPhone(juridicProfileRequestDTO.commercialPhone());
        juridicProfile.setWebsite(juridicProfileRequestDTO.website());
        juridicProfile.setCommercialEmail(juridicProfileRequestDTO.commercialEmail());
        juridicProfile.setCompanyDescription(juridicProfileRequestDTO.companyDescription());
        juridicProfile.setCompanyLogo(juridicProfileRequestDTO.companyLogo());
        juridicProfile.setLegalRepresentativeContactName(juridicProfileRequestDTO.legalRepresentativeContactName());
        juridicProfile.setLegalRepresentativeContactEmail(juridicProfileRequestDTO.legalRepresentativeContactEmail());
        juridicProfile.setLegalRepresentativeContactPhone(juridicProfileRequestDTO.legalRepresentativeContactPhone());
        juridicProfile.setCreatedAt(LocalDateTime.now());
        juridicProfile.setUpdatedAt(LocalDateTime.now());

        this.juridicProfileRepository.save(juridicProfile);

        return new JuridicProfileIdDTO(juridicProfile.getId());
    }
}
