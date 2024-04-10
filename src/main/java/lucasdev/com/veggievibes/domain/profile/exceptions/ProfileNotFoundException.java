package lucasdev.com.veggievibes.domain.profile.exceptions;

public class ProfileNotFoundException extends RuntimeException {

    public ProfileNotFoundException(String message) {
        super(message);
    }
}
