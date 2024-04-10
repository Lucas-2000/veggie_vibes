package lucasdev.com.veggievibes.dto.profile;

import lucasdev.com.veggievibes.dto.user.UserResponseDTO;

public record ProfileResponseDTO(String id, UserResponseDTO user, String cpf, String firstName, String lastName, String phoneNumber, String address, String city, String state, String postalCode) {
}
