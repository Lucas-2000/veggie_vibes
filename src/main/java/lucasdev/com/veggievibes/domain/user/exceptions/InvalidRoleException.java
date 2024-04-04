package lucasdev.com.veggievibes.domain.user.exceptions;

public class InvalidRoleException extends RuntimeException {

    public InvalidRoleException(String message) {
        super(message);
    }
}
