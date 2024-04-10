package lucasdev.com.veggievibes.services;

import lucasdev.com.veggievibes.domain.profile.Profile;
import lucasdev.com.veggievibes.domain.profile.exceptions.InvalidCPFFormatException;
import lucasdev.com.veggievibes.domain.profile.exceptions.ProfileAlreadyExistsException;
import lucasdev.com.veggievibes.domain.user.exceptions.UserNotFoundException;
import lucasdev.com.veggievibes.dto.profile.ProfileIdDTO;
import lucasdev.com.veggievibes.dto.profile.ProfileRequestDTO;
import lucasdev.com.veggievibes.repositories.ProfileRepository;
import lucasdev.com.veggievibes.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProfileService {

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private UserRepository userRepository;

    private final String REGEX_CPF = "\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}";

    public ProfileIdDTO create(ProfileRequestDTO profileRequestDTO) {
        var userExists = this.userRepository.findById(profileRequestDTO.userId());

        if(userExists.isEmpty()) throw new UserNotFoundException("User not found");

        var profileExists = this.profileRepository.findByUserId(profileRequestDTO.userId());

        if(profileExists.isPresent()) throw new ProfileAlreadyExistsException("User already have profile");

        if(!profileRequestDTO.cpf().matches(REGEX_CPF)) throw new InvalidCPFFormatException("Invalid CPF format, the correct is XXX.XXX.XXX-XX");

        Profile newProfile = new Profile();
        newProfile.setUser(userExists.get());
        newProfile.setCpf(profileRequestDTO.cpf());
        newProfile.setFirstName(profileRequestDTO.firstName());
        newProfile.setLastName(profileRequestDTO.lastName());
        newProfile.setPhoneNumber(profileRequestDTO.phoneNumber());
        newProfile.setAddress(profileRequestDTO.address());
        newProfile.setCity(profileRequestDTO.city());
        newProfile.setState(profileRequestDTO.state());
        newProfile.setPostalCode(profileRequestDTO.postalCode());

        this.profileRepository.save(newProfile);

        return new ProfileIdDTO(newProfile.getId());
    }
}
