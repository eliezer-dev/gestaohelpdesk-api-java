package dev.eliezer.superticket.controller.exception;

import dev.eliezer.superticket.service.exception.BusinessException;
import dev.eliezer.superticket.service.exception.NotFoundException;
import jakarta.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<String> handleBusinessException(BusinessException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<String> handleNoContentException(NotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)

    public ResponseEntity<List> handleMethodNoContentException(MethodArgumentNotValidException e) {

        List error = new ArrayList();
        e.getBindingResult().getFieldErrors().forEach(fieldError -> {
            error.add(fieldError.getDefaultMessage());
        });
        return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
        }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Set> handleConstraintViolationException(ConstraintViolationException e) {

        Set<String> error = new HashSet<>();
        e.getConstraintViolations().forEach(fieldError -> {
            error.add(fieldError.getMessageTemplate());
        });

        return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    //Essa exception foi adicionada para quando ocorrer de existir um cliente com cpf/cnpj já cadastrado em outro cliente
    //mas ela sempre ocorre quando um campo estiver marcado como unico no banco, havendo já uma ocorrência dessa informação
    //alguém tentra gravar ela novamente.
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public ResponseEntity<String> handleConstraintViolationException(SQLIntegrityConstraintViolationException e){
        String error = "Erro ao salvar os dados no banco de dados.";
        e.printStackTrace();
        if (e.getMessage().contains("CPF_OR_CNPJ") || e.getMessage().contains("CPF") ){
            error = "CPF ou CNPJ já informado em outro cadastro.";
        }
        return new ResponseEntity<>(error, HttpStatus.UNPROCESSABLE_ENTITY);
    }
}






//
//    @ExceptionHandler(Throwable.class)
//    public ResponseEntity<String> handleUnexpectedException(Throwable unexpectedException) {
//        String message = "Unexpected server error.";
//        LOGGER.error(message, unexpectedException);
//        return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
//    }


