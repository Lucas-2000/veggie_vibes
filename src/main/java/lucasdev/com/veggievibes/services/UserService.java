package lucasdev.com.veggievibes.services;

import lucasdev.com.veggievibes.domain.user.User;
import lucasdev.com.veggievibes.domain.user.exceptions.EmailAlreadyExistsException;
import lucasdev.com.veggievibes.domain.user.exceptions.InvalidRoleException;
import lucasdev.com.veggievibes.domain.user.exceptions.ArePasswordAndRePasswordNotEqualException;
import lucasdev.com.veggievibes.domain.user.exceptions.PasswordLengthException;
import lucasdev.com.veggievibes.dto.user.UserIdDTO;
import lucasdev.com.veggievibes.dto.user.UserRequestDTO;
import lucasdev.com.veggievibes.repositories.UserRepository;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public UserIdDTO create(UserRequestDTO userRequestDTO) {
        var emailExists = this.userRepository.findByEmail(userRequestDTO.email());

        if(emailExists.isPresent()) throw new EmailAlreadyExistsException("Email already exists");

        if(userRequestDTO.password().length() < 8 || userRequestDTO.rePassword().length() < 8) throw new PasswordLengthException("Password and RePassword length must be higher than 7 characters");

        if (!userRequestDTO.password().equals(userRequestDTO.rePassword())) throw new ArePasswordAndRePasswordNotEqualException("Password and RePassword must be equals");

        if(!userRequestDTO.role().toUpperCase().equals("ADMIN") && !userRequestDTO.role().toUpperCase().equals("USER") && !userRequestDTO.role().toUpperCase().equals("SELLER")) throw new InvalidRoleException("Invalid role");

        String hashedPassword = BCrypt.hashpw(userRequestDTO.password(), BCrypt.gensalt());

        User newUser = new User();
        newUser.setName(userRequestDTO.name());
        newUser.setEmail(userRequestDTO.email());
        newUser.setPassword(hashedPassword);
        newUser.setRole(userRequestDTO.role());
        newUser.setEmailValidated(false);
        newUser.setRole(userRequestDTO.role());
        newUser.setCreatedAt(LocalDateTime.now());
        newUser.setUpdatedAt(LocalDateTime.now());

        this.userRepository.save(newUser);

        return new UserIdDTO(newUser.getId());
    }
}
