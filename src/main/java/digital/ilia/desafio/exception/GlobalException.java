package digital.ilia.desafio.exception;

import digital.ilia.desafio.model.MensagemModel;
import lombok.Getter;
import org.springframework.http.HttpStatus;

public class GlobalException  extends Exception {
    @Getter
    private MensagemModel mensagem;
    @Getter
    private  HttpStatus status;

    public  GlobalException (MensagemModel msg, HttpStatus status){
       super(msg.getMensagem());
       this.mensagem = msg;
       this.status = status;
   }
}
