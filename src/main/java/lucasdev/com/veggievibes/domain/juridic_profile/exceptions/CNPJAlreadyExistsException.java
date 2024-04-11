package lucasdev.com.veggievibes.domain.juridic_profile.exceptions;

public class CNPJAlreadyExistsException extends RuntimeException {

    public CNPJAlreadyExistsException(String message) {
        super(message);
    }
}
