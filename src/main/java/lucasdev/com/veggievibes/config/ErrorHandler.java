package lucasdev.com.veggievibes.config;

import lucasdev.com.veggievibes.domain.juridic_profile.exceptions.CNPJAlreadyExistsException;
import lucasdev.com.veggievibes.domain.juridic_profile.exceptions.InvalidCNPJFormatException;
import lucasdev.com.veggievibes.domain.juridic_profile.exceptions.JuridicProfileAlreadyExistsException;
import lucasdev.com.veggievibes.domain.juridic_profile.exceptions.JuridicProfileNotFoundException;
import lucasdev.com.veggievibes.domain.profile.exceptions.CPFAlreadyExistsException;
import lucasdev.com.veggievibes.domain.profile.exceptions.InvalidCPFFormatException;
import lucasdev.com.veggievibes.domain.profile.exceptions.ProfileAlreadyExistsException;
import lucasdev.com.veggievibes.domain.profile.exceptions.ProfileNotFoundException;
import lucasdev.com.veggievibes.domain.reset_password.exceptions.InvalidTokenTimeException;
import lucasdev.com.veggievibes.domain.reset_password.exceptions.ResetPasswordNotFoundException;
import lucasdev.com.veggievibes.domain.user.exceptions.*;
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

    @ExceptionHandler(IncorrectLoginException.class)
    public ResponseEntity handleIncorrectLoginException(IncorrectLoginException exception) {
        return ResponseEntity.badRequest().body(new ErrorResponseDTO(exception.getMessage()));
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity handleUserNotFound(UserNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponseDTO(exception.getMessage()));
    }

    @ExceptionHandler(ProfileNotFoundException.class)
    public ResponseEntity handleProfileNotFoundE(ProfileNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponseDTO(exception.getMessage()));
    }

    @ExceptionHandler(InvalidCPFFormatException.class)
    public ResponseEntity handleInvalidCPFFormat(InvalidCPFFormatException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponseDTO(exception.getMessage()));
    }

    @ExceptionHandler(ProfileAlreadyExistsException.class)
    public ResponseEntity handleProfileAlreadyExists(ProfileAlreadyExistsException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponseDTO(exception.getMessage()));
    }

    @ExceptionHandler(CPFAlreadyExistsException.class)
    public ResponseEntity handleCPFAlreadyExists(CPFAlreadyExistsException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponseDTO(exception.getMessage()));
    }

    @ExceptionHandler(JuridicProfileAlreadyExistsException.class)
    public ResponseEntity handleJuridicProfileAlreadyExists(JuridicProfileAlreadyExistsException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponseDTO(exception.getMessage()));
    }

    @ExceptionHandler(CNPJAlreadyExistsException.class)
    public ResponseEntity handleCNPJAlreadyExists(CNPJAlreadyExistsException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponseDTO(exception.getMessage()));
    }

    @ExceptionHandler(InvalidCNPJFormatException.class)
    public ResponseEntity handleInvalidCNPJFormat(InvalidCNPJFormatException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponseDTO(exception.getMessage()));
    }

    @ExceptionHandler(JuridicProfileNotFoundException.class)
    public ResponseEntity handleJuridicProfileNotFound(JuridicProfileNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponseDTO(exception.getMessage()));
    }

    @ExceptionHandler(ResetPasswordNotFoundException.class)
    public ResponseEntity handleResetPasswordNotFound(ResetPasswordNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponseDTO(exception.getMessage()));
    }

    @ExceptionHandler(InvalidTokenTimeException.class)
    public ResponseEntity handleInvalidTokenTime(InvalidTokenTimeException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponseDTO(exception.getMessage()));
    }
}
