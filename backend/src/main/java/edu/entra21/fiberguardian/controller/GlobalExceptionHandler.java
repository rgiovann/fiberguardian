//package edu.entra21.fiberguardian.controller;
//
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.AccessDeniedException;
//import org.springframework.security.web.csrf.InvalidCsrfTokenException;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.RestControllerAdvice;
//
//@RestControllerAdvice
//public class GlobalExceptionHandler {
//
//    @ExceptionHandler(AccessDeniedException.class)
//    public ResponseEntity<String> handleAccessDeniedException(AccessDeniedException ex) {
//        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid CSRF Token");
//    }
//
//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<String> handleGeneralException(Exception ex) {
//        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Server Error: " + ex.getMessage());
//    }
//
//    @ExceptionHandler(NullPointerException.class)
//    public ResponseEntity<String> handleNullPointerException(NullPointerException ex) {
//        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Null Pointer Error: " + ex.getMessage());
//    }
//
//    @ExceptionHandler(InvalidCsrfTokenException.class)
//    public ResponseEntity<String> handleInvalidCsrfTokenException(InvalidCsrfTokenException ex) {
//        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid CSRF Token: " + ex.getMessage());
//    }
//}
