package lucasdev.com.veggievibes.domain.profile.exceptions;

public class ProfileAlreadyExistsException extends RuntimeException {

    public ProfileAlreadyExistsException(String message) {
        super(message);
    }
}
