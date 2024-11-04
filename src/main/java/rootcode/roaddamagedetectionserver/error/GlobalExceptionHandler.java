package rootcode.roaddamagedetectionserver.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import rootcode.roaddamagedetectionserver.common.ResponseCode;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler({
            IllegalArgumentException.class,
            IllegalStateException.class,

    })
    public ResponseEntity<ErrorResponse> handleBadRequestException(Exception e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(
                        ResponseCode._BAD_REQUEST.getCode(),
                        e.getMessage()
                ));
    }

    @ExceptionHandler({
            GeneralException.class,
    })
    public ResponseEntity<ErrorResponse> handleGeneralException(GeneralException e) {
        return ResponseEntity
                .status(e.getErrorCode().getHttpStatus())
                .body(new ErrorResponse(
                        e.getErrorCode().getCode(),
                        e.getMessage()
                ));
    }

    @ExceptionHandler({
            Exception.class
    })
    public ResponseEntity<ErrorResponse> handleException(Exception e) {
        return ResponseEntity.internalServerError()
                .body(new ErrorResponse(
                        ResponseCode._INTERNAL_SERVER_ERROR.getCode(),
                        e.getMessage()
                ));
    }
}
