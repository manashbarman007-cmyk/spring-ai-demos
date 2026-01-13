package in.sp.main.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import in.sp.main.payload.ApiResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(NoSuchTicketException.class)
	public ResponseEntity<ApiResponse> handleError(NoSuchTicketException ex) {
		
		ApiResponse response = ApiResponse
				               .builder()
				               .message(ex.getMessage())
				               .httpStatus(HttpStatus.NOT_FOUND)
				               .build();
		
		return new ResponseEntity<ApiResponse>(response, HttpStatus.NOT_FOUND);
		
	}

}
