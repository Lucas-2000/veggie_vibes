package lucasdev.com.veggievibes.domain.reset_password.exceptions;

public class ResetPasswordNotFoundException extends RuntimeException {

    public ResetPasswordNotFoundException(String message) {
        super(message);
    }
}
