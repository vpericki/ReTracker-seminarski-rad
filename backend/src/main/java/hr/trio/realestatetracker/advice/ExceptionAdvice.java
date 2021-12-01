package hr.trio.realestatetracker.advice;

import hr.trio.realestatetracker.dto.ApiResponse;
import hr.trio.realestatetracker.exception.NotFoundException;
import hr.trio.realestatetracker.exception.PermissionException;
import java.util.NoSuchElementException;
import liquibase.exception.DatabaseException;
import org.springframework.core.convert.ConversionException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler({NotFoundException.class})
    public ResponseEntity<ApiResponse> handleNotFoundException(final RuntimeException e) {
        return new ResponseEntity<>(new ApiResponse(e.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({IllegalArgumentException.class,
            IllegalAccessException.class,
            NoSuchElementException.class,
            DatabaseException.class,
            BadCredentialsException.class})
    public ResponseEntity<ApiResponse> handleBadRequestException(final RuntimeException e) {
        return new ResponseEntity<>(new ApiResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ConversionException.class})
    public ResponseEntity<ApiResponse> handleInternalServerErrorException(final RuntimeException e) {
        return new ResponseEntity<>(new ApiResponse(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({PermissionException.class})
    public ResponseEntity<ApiResponse> handlePermissionException(final RuntimeException e) {
        return new ResponseEntity<>(new ApiResponse(e.getMessage()), HttpStatus.FORBIDDEN);
    }

}