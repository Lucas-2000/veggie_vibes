package lucasdev.com.veggievibes.dto.user;

public record UserRequestDTO(String name, String email, String password, String rePassword, String role) {
}
