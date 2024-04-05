package lucasdev.com.veggievibes.dto.user;

public record UserResponseDTO(String id, String name, String email, boolean isEmailValidated, String role) {
}
