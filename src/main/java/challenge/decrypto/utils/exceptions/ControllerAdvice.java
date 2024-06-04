package challenge.decrypto.utils.exceptions;

import challenge.decrypto.models.exceptions.ErrorDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerAdvice {

    /**
     * A method to handle RuntimeException and return an ErrorDTO with a 400 status code.
     *
     * @param  ex   the RuntimeException to handle
     * @return      the ResponseEntity containing the ErrorDTO and HttpStatus.BAD_REQUEST
     */
    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity<String> runtimeExceptionHandler(RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    /**
     * A method to handle BusinessException and return an ErrorDTO with a 400 status code.
     *
     * @param  ex   the BusinessException to handle
     * @return      the ResponseEntity containing the ErrorDTO and HttpStatus.BAD_REQUEST
     */
    @ExceptionHandler(value = BadRequestException.class)
    public ResponseEntity<ErrorDTO> badRequestExceptionHandler(BadRequestException ex) {
        ErrorDTO error = ErrorDTO.builder().code("400").message(ex.getMessage()).build();
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = NotFoundException.class)
    public ResponseEntity<ErrorDTO> entityNotFoundExceptionHandler(NotFoundException ex) {
        ErrorDTO error = ErrorDTO.builder().code("404").message(ex.getMessage()).build();
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
}
