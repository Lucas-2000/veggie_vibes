package lucasdev.com.veggievibes.domain.juridic_profile.exceptions;

public class InvalidCNPJFormatException extends RuntimeException {

    public InvalidCNPJFormatException(String message) {
        super(message);
    }
}
