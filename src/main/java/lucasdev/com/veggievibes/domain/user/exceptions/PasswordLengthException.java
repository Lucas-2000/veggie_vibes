package lucasdev.com.veggievibes.domain.user.exceptions;

public class PasswordLengthException extends RuntimeException {

    public PasswordLengthException(String message) {
        super(message);
    }
}
