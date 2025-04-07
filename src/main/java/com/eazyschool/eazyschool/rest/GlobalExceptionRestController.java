package com.eazyschool.eazyschool.rest;


import com.eazyschool.eazyschool.model.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Order(1)
@Slf4j
@RestControllerAdvice(annotations = RestController.class)
public class GlobalExceptionRestController extends ResponseEntityExceptionHandler {

    @Override
    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers ,
                                                                HttpStatusCode httpStatusCode , WebRequest webRequest){

        Response response = new Response(httpStatusCode.toString(),ex.getBindingResult().toString());

        return new ResponseEntity(response,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<Response> exceptionHandler(Exception exception){
        Response response = new Response("500",exception.getMessage());
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(response);
    }
}
