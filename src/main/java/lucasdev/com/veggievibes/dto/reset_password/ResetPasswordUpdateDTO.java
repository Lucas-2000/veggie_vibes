package lucasdev.com.veggievibes.dto.reset_password;

public record ResetPasswordUpdateDTO(String token, String email, String password, String rePassword) {
}
