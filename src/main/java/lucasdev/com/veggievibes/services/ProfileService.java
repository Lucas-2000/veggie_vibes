package lucasdev.com.veggievibes.services;

import lucasdev.com.veggievibes.domain.profile.Profile;
import lucasdev.com.veggievibes.domain.profile.exceptions.CPFAlreadyExistsException;
import lucasdev.com.veggievibes.domain.profile.exceptions.InvalidCPFFormatException;
import lucasdev.com.veggievibes.domain.profile.exceptions.ProfileAlreadyExistsException;
import lucasdev.com.veggievibes.domain.profile.exceptions.ProfileNotFoundException;
import lucasdev.com.veggievibes.domain.user.User;
import lucasdev.com.veggievibes.domain.user.exceptions.InvalidRoleException;
import lucasdev.com.veggievibes.domain.user.exceptions.UserNotFoundException;
import lucasdev.com.veggievibes.dto.profile.ProfileIdDTO;
import lucasdev.com.veggievibes.dto.profile.ProfileRequestDTO;
import lucasdev.com.veggievibes.dto.profile.ProfileResponseDTO;
import lucasdev.com.veggievibes.dto.profile.ProfileUpdateDTO;
import lucasdev.com.veggievibes.dto.user.UserResponseDTO;
import lucasdev.com.veggievibes.repositories.ProfileRepository;
import lucasdev.com.veggievibes.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class ProfileService {

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private UserRepository userRepository;

    private final String REGEX_CPF = "\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}";

    public ProfileIdDTO create(ProfileRequestDTO profileRequestDTO) {
        User userExists = this.checkUserExists(profileRequestDTO.userId());

        var profileExists = this.profileRepository.findByUserId(profileRequestDTO.userId());

        if(profileExists.isPresent()) throw new ProfileAlreadyExistsException("User already have profile");

        if(!userExists.getRole().equals("USER")) throw new InvalidRoleException("Invalid user role, needs to be USER");

        if(!profileRequestDTO.cpf().matches(REGEX_CPF)) throw new InvalidCPFFormatException("Invalid CPF format, the correct is XXX.XXX.XXX-XX");

        Profile newProfile = new Profile();
        newProfile.setUser(userExists);
        newProfile.setCpf(profileRequestDTO.cpf());
        newProfile.setFirstName(profileRequestDTO.firstName());
        newProfile.setLastName(profileRequestDTO.lastName());
        newProfile.setPhoneNumber(profileRequestDTO.phoneNumber());
        newProfile.setAddress(profileRequestDTO.address());
        newProfile.setCity(profileRequestDTO.city());
        newProfile.setState(profileRequestDTO.state());
        newProfile.setPostalCode(profileRequestDTO.postalCode());
        newProfile.setCreatedAt(LocalDateTime.now());
        newProfile.setUpdatedAt(LocalDateTime.now());

        this.profileRepository.save(newProfile);

        return new ProfileIdDTO(newProfile.getId());
    }

    public ProfileResponseDTO findProfileByUserId(String userId) {
        User userExists = this.checkUserExists(userId);

        Optional<Profile> profile = this.profileRepository.findByUserId(userId);

        if(profile.isEmpty()) throw new ProfileNotFoundException("Profile Not Found");

        UserResponseDTO userResponseDTO = new UserResponseDTO(userExists.getId(), userExists.getName(), userExists.getEmail(), userExists.isEmailValidated(), userExists.getRole());

        ProfileResponseDTO profileResponseDTO = new ProfileResponseDTO(
                profile.get().getId(),
                userResponseDTO,
                profile.get().getCpf(),
                profile.get().getFirstName(),
                profile.get().getLastName(),
                profile.get().getPhoneNumber(),
                profile.get().getAddress(),
                profile.get().getCity(),
                profile.get().getState(),
                profile.get().getPostalCode()
                );

        return profileResponseDTO;
    }

    public ProfileIdDTO update(String id, ProfileUpdateDTO profileUpdateDTO) {
        Optional<Profile> profile = this.profileRepository.findById(id);

        if(profile.isEmpty()) throw new ProfileNotFoundException("Profile Not Found");

        if(!profileUpdateDTO.cpf().matches(REGEX_CPF)) throw new InvalidCPFFormatException("Invalid CPF format, the correct is XXX.XXX.XXX-XX");

        if (!profileUpdateDTO.cpf().equals(profile.get().getCpf())) {
            Optional<Profile> existingProfile = this.profileRepository.findByCpf(profileUpdateDTO.cpf());
            if (existingProfile.isPresent()) {
                throw new CPFAlreadyExistsException("CPF already exists in another profile");
            }

            profile.get().setCpf(profileUpdateDTO.cpf());
        }

        profile.get().setFirstName(profileUpdateDTO.firstName());
        profile.get().setLastName(profileUpdateDTO.lastName());
        profile.get().setPhoneNumber(profileUpdateDTO.phoneNumber());
        profile.get().setAddress(profileUpdateDTO.address());
        profile.get().setCity(profileUpdateDTO.city());
        profile.get().setState(profileUpdateDTO.state());
        profile.get().setPostalCode(profileUpdateDTO.postalCode());
        profile.get().setUpdatedAt(LocalDateTime.now());

        this.profileRepository.save(profile.get());

        return new ProfileIdDTO(id);
    }

    private User checkUserExists(String userId) {
        var userExists = this.userRepository.findById(userId);

        if(userExists.isEmpty()) throw new UserNotFoundException("User not found");

        return userExists.get();
    }
}
