package cl.everis.cliente.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ErrorException extends RuntimeException{

    private static final long serialVersionUID = 1L;
    private final HttpStatus httpStatus;
    private final String message;

    public ErrorException(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }
}
