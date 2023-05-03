package com.example.mediumclone.exception;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {


  public ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
    return new ResponseEntity<>(apiError, apiError.getHttpStatus());
  }

  @ExceptionHandler(value = UserNotFoundException.class)
  public ResponseEntity<Object> userNotFoundException(UserNotFoundException exception) {
    return buildResponseEntity(
        new ApiError(LocalDateTime.now(), HttpStatus.NOT_FOUND, 404, "User Not Found"));
  }

  @ExceptionHandler(value = PostNotFoundException.class)
  public ResponseEntity<Object> postNotFoundException(PostNotFoundException exception) {
    return buildResponseEntity(
        new ApiError(LocalDateTime.now(), HttpStatus.NOT_FOUND, 404, "Post Not Found"));
  }

  @ExceptionHandler(value = CommentNotFoundException.class)
  public ResponseEntity<Object> commentNotFoundException(CommentNotFoundException exception) {
    return buildResponseEntity(
        new ApiError(LocalDateTime.now(), HttpStatus.NOT_FOUND, 404, "Comment Not Found"));
  }

  @ExceptionHandler(value = TokenExpiredException.class)
  public ResponseEntity<Object> tokenExpiredException(TokenExpiredException exception) {
    return buildResponseEntity(
        new ApiError(LocalDateTime.now(), HttpStatus.UNAUTHORIZED, 401, "Token Expired"));
  }

  @ExceptionHandler(value = ConfirmPasswordNotEqualsException.class)
  public ResponseEntity<Object> confirmPasswordException(
      ConfirmPasswordNotEqualsException exception) {
    return buildResponseEntity(
        new ApiError(LocalDateTime.now(), HttpStatus.BAD_REQUEST, 400, "Password Not Equal"));
  }


  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(
      MethodArgumentNotValidException ex,
      HttpHeaders headers, HttpStatus status, WebRequest request) {

    Map<String, Object> responseBody = new LinkedHashMap<>();
    responseBody.put("Timestamp", new Date());
    responseBody.put("Status", status.value());

    List<String> errors = ex.getBindingResult().getFieldErrors()
        .stream()
        .map(x -> x.getDefaultMessage())
        .collect(Collectors.toList());

    responseBody.put("Errors", errors);

    return new ResponseEntity<>(responseBody, headers, status);
  }


}
