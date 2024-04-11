package lucasdev.com.veggievibes.domain.juridic_profile.exceptions;

public class JuridicProfileAlreadyExistsException extends RuntimeException {

    public JuridicProfileAlreadyExistsException(String message) {
        super(message);
    }
}
