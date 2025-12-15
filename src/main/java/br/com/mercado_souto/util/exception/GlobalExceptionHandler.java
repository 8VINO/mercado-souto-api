package br.com.mercado_souto.util.exception;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleInternalServerError(Exception ex) {

        return ResponseEntity.internalServerError().body(ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationExceptions(MethodArgumentNotValidException ex) {

        Map<String, Object> response = new HashMap<>();
        List<Map<String, String>> errors = new ArrayList<>();

        ex.getBindingResult().getAllErrors().forEach((error) -> {
            Map<String, String> errorDetails = new HashMap<>();
            errorDetails.put("fieldName", ((FieldError) error).getField());
            errorDetails.put("defaultMessage", error.getDefaultMessage());
            errors.add(errorDetails);
        });

        response.put("errors", errors);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DataAlreadyExistsException.class)
    public ResponseEntity<Map<String, Object>> handleDataAlreadyExists(
            DataAlreadyExistsException ex) {

        Map<String, Object> response = new HashMap<>();
        response.put("message", ex.getMessage());
        response.put("status", HttpStatus.CONFLICT.value());

        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleEntityNotFound(
            EntityNotFoundException ex) {

        Map<String, Object> response = new HashMap<>();
        response.put("message", ex.getMessage());
        response.put("status", HttpStatus.NOT_FOUND.value());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Map<String, Object>> handleBadCredentials(
            BadCredentialsException ex) {

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Invalid email or password");
        response.put("status", HttpStatus.UNAUTHORIZED.value());

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

   @ExceptionHandler(BusinessRuleException.class)
    public ResponseEntity<Map<String, Object>> handleBusinessRuleException(
            BusinessRuleException ex) {

        HttpStatus status = HttpStatus.FORBIDDEN; 
        
        Map<String, Object> response = new HashMap<>();
        response.put("message", ex.getMessage());
        response.put("status", status.value()); 

        return ResponseEntity.status(status).body(response);
    }

}
