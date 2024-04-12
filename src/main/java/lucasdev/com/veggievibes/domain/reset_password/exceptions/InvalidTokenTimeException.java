package lucasdev.com.veggievibes.domain.reset_password.exceptions;

public class InvalidTokenTimeException extends RuntimeException {

    public InvalidTokenTimeException(String message) {
        super(message);
    }
}
