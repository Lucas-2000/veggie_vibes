package lucasdev.com.veggievibes.dto.profile;

public record ProfileUpdateDTO(String cpf, String firstName, String lastName, String phoneNumber, String address, String city, String state, String postalCode) {
}
