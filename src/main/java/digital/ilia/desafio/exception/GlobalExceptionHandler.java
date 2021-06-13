package digital.ilia.desafio.exception;

import digital.ilia.desafio.model.MensagemModel;
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
    @ExceptionHandler(GlobalException.class)
    public ResponseEntity<MensagemModel> handleGlobalException(
            GlobalException globalException,
            WebRequest request
    ){
        return ResponseEntity.status(globalException.getStatus()).body(globalException.getMensagem());
    }


    @Override
    public ResponseEntity<Object> handleExceptionInternal(
            Exception ex,
            Object body,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {

        if (ex instanceof MethodArgumentNotValidException) {
            MethodArgumentNotValidException methodArgumentNotValidException = (MethodArgumentNotValidException) ex;
            var msg =methodArgumentNotValidException.getBindingResult().getAllErrors().get(0).getDefaultMessage();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MensagemModel(msg));
        }
        var ff = super.handleExceptionInternal(ex,body,headers,status,request);;
        return ff;

    }

}