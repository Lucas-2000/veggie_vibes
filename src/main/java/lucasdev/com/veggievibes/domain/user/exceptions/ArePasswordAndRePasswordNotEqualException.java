package lucasdev.com.veggievibes.domain.user.exceptions;

public class ArePasswordAndRePasswordNotEqualException extends RuntimeException {

    public ArePasswordAndRePasswordNotEqualException(String message) {
        super(message);
    }
}
