package lucasdev.com.veggievibes.domain.profile.exceptions;

public class InvalidCPFFormatException extends RuntimeException {

    public InvalidCPFFormatException(String message) {
        super(message);
    }
}
