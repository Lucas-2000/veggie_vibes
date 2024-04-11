package lucasdev.com.veggievibes.services;

import lucasdev.com.veggievibes.domain.juridic_profile.JuridicProfile;
import lucasdev.com.veggievibes.domain.juridic_profile.exceptions.InvalidCNPJFormatException;
import lucasdev.com.veggievibes.domain.juridic_profile.exceptions.JuridicProfileAlreadyExistsException;
import lucasdev.com.veggievibes.domain.juridic_profile.exceptions.JuridicProfileNotFoundException;
import lucasdev.com.veggievibes.domain.user.User;
import lucasdev.com.veggievibes.domain.user.exceptions.InvalidRoleException;
import lucasdev.com.veggievibes.domain.user.exceptions.UserNotFoundException;
import lucasdev.com.veggievibes.dto.juridic_profile.JuridicProfileIdDTO;
import lucasdev.com.veggievibes.dto.juridic_profile.JuridicProfileRequestDTO;
import lucasdev.com.veggievibes.dto.juridic_profile.JuridicProfileResponseDTO;
import lucasdev.com.veggievibes.dto.user.UserResponseDTO;
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
        User userExists = this.checkUserExists(juridicProfileRequestDTO.userId());

        Optional<JuridicProfile> juridicProfileExists = this.juridicProfileRepository.findByUserId(juridicProfileRequestDTO.userId());

        if(juridicProfileExists.isPresent()) throw new JuridicProfileAlreadyExistsException("User already have profile");

        if(!userExists.getRole().equals("SELLER")) throw new InvalidRoleException("Invalid user role, needs to be SELLER");

        if(!juridicProfileRequestDTO.cnpj().matches(REGEX_CNPJ)) throw new InvalidCNPJFormatException("Invalid CNPJ format, the correct is XX.XXX.XXX/XXXX-XX");

        JuridicProfile juridicProfile = new JuridicProfile();
        juridicProfile.setUser(userExists);
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

    public JuridicProfileResponseDTO findJuridicProfileByUserId(String userId) {
        User userExists = this.checkUserExists(userId);

        Optional<JuridicProfile> juridicProfile = this.juridicProfileRepository.findByUserId(userId);

        if(juridicProfile.isEmpty()) throw new JuridicProfileNotFoundException("Juridic Profile Not Found");

        UserResponseDTO userResponseDTO = new UserResponseDTO(userExists.getId(), userExists.getName(), userExists.getEmail(), userExists.isEmailValidated(), userExists.getRole());

        JuridicProfileResponseDTO profileResponseDTO = new JuridicProfileResponseDTO(
                juridicProfile.get().getId(),
                userResponseDTO,
                juridicProfile.get().getLegalName(),
                juridicProfile.get().getCnpj(),
                juridicProfile.get().getStateRegistration(),
                juridicProfile.get().getTradeName(),
                juridicProfile.get().getActivitySector(),
                juridicProfile.get().getCommercialAddress(),
                juridicProfile.get().getCommercialPhone(),
                juridicProfile.get().getWebsite(),
                juridicProfile.get().getCommercialEmail(),
                juridicProfile.get().getCompanyDescription(),
                juridicProfile.get().getCompanyLogo(),
                juridicProfile.get().getLegalRepresentativeContactName(),
                juridicProfile.get().getLegalRepresentativeContactEmail(),
                juridicProfile.get().getLegalRepresentativeContactPhone()
        );

        return profileResponseDTO;
    }

    private User checkUserExists(String userId) {
        var userExists = this.userRepository.findById(userId);

        if(userExists.isEmpty()) throw new UserNotFoundException("User not found");

        return userExists.get();
    }
}
