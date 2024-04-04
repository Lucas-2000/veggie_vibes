package lucasdev.com.veggievibes.config;

import lucasdev.com.veggievibes.domain.user.exceptions.EmailAlreadyExistsException;
import lucasdev.com.veggievibes.domain.user.exceptions.InvalidRoleException;
import lucasdev.com.veggievibes.domain.user.exceptions.ArePasswordAndRePasswordNotEqualException;
import lucasdev.com.veggievibes.domain.user.exceptions.PasswordLengthException;
import lucasdev.com.veggievibes.dto.general.ErrorResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity handleEmailAlreadyExists(EmailAlreadyExistsException exception) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorResponseDTO(exception.getMessage()));
    }

    @ExceptionHandler(PasswordLengthException.class)
    public ResponseEntity handlePasswordLength(PasswordLengthException exception) {
        return ResponseEntity.badRequest().body(new ErrorResponseDTO(exception.getMessage()));
    }

    @ExceptionHandler(ArePasswordAndRePasswordNotEqualException.class)
    public ResponseEntity handleIsPasswordAndRePasswordNotEqual(ArePasswordAndRePasswordNotEqualException exception) {
        return ResponseEntity.badRequest().body(new ErrorResponseDTO(exception.getMessage()));
    }

    @ExceptionHandler(InvalidRoleException.class)
    public ResponseEntity handleInvalidRole(InvalidRoleException exception) {
        return ResponseEntity.badRequest().body(new ErrorResponseDTO(exception.getMessage()));
    }
}
