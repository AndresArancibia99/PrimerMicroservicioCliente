package cl.everis.cliente.exception;

import cl.everis.cliente.dto.ErrorDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class ErrorHandler {
    @ExceptionHandler(ErrorException.class)
  public ResponseEntity<?>  manageErrorCustomException(ErrorException exception){
        ErrorDTO errorDetailsDTO =
                new ErrorDTO(String.valueOf(exception.getHttpStatus().value()), exception.getMessage());
        return new ResponseEntity<>(errorDetailsDTO, exception.getHttpStatus());
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> methodArgumentNotValidException(HttpServletRequest request, MethodArgumentNotValidException e) {
        BindingResult result = e.getBindingResult();

        Map<String, Object> response = new HashMap<>();

        List<String> fieldErrors = result.getFieldErrors().stream().
                map(err ->"El campo [" + err.getField() + "] " + err.getDefaultMessage()).collect(Collectors.toList());

        response.put("errors", fieldErrors);

        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);

    }


}
