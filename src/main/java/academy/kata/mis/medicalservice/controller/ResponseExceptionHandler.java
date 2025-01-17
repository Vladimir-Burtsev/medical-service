package academy.kata.mis.medicalservice.controller;

import academy.kata.mis.medicalservice.exceptions.*;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.NoSuchElementException;

@Slf4j
@RestControllerAdvice
public class ResponseExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(AuthException.class)
    protected ResponseEntity<String> handleAuthExc(AuthException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(TokenException.class)
    protected ResponseEntity<String> handleTokenExc(TokenException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Object> handleAccessDeniedException(Exception ex, WebRequest request) {
        return new ResponseEntity<>(
                "Доступ запрещен, недостаточно прав", new HttpHeaders(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(LogicException.class)
    protected ResponseEntity<Object> handleConflict(LogicException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<String> handleAuthExc(NoSuchElementException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(FeignException.class)
    public ResponseEntity<String> handleFeignExc(FeignException ex) {
        log.error("Ошибка при запросе в другой сервис: {}", ex.getMessage());
        return new ResponseEntity<>("В данный момент невозможно получить весь спектр запрашиваемых данных.",
                HttpStatus.UNPROCESSABLE_ENTITY);
    }
}