package lucasdev.com.veggievibes.domain.user.exceptions;

public class IncorrectLoginException extends RuntimeException {

    public IncorrectLoginException(String message) {
        super(message);
    }
}
