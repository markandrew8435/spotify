package voosh.ai.spotify.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ErrorResponse> handleApiException(ApiException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), ex.httpStatusCode.value());
        return new ResponseEntity<>(errorResponse, ex.httpStatusCode);
    }

    @Getter
    @Setter
    static class ErrorResponse {
        private int status;
        private String error;
        public ErrorResponse(String message, int status) {
            this.error = message;
            this.status = status;
        }

    }
}
