package lucasdev.com.veggievibes.domain.profile.exceptions;

public class CPFAlreadyExistsException extends RuntimeException{

    public CPFAlreadyExistsException(String message) {
        super(message);
    }
}
