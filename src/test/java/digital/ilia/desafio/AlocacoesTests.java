package digital.ilia.desafio;

import com.fasterxml.jackson.databind.ObjectMapper;
import digital.ilia.desafio.exception.Errors;
import digital.ilia.desafio.model.AlocacaoModel;
import digital.ilia.desafio.model.MensagemModel;
import digital.ilia.desafio.model.MomentoModel;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class AlocacoesTests {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Test
    public void Ok() throws Exception {
        this.mockMvc.perform(post("/batidas")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(new MomentoModel("2018-08-22T08:00:00"))))
                .andDo(print())
                .andExpect(status().isOk());
        this.mockMvc.perform(post("/batidas")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(new MomentoModel("2018-08-22T11:00:00"))))
                .andDo(print())
                .andExpect(status().isOk());
        this.mockMvc.perform(post("/batidas")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
               .content(objectMapper.writeValueAsString(new MomentoModel("2018-08-22T12:00:00"))))
                .andDo(print())
                .andExpect(status().isOk());
        this.mockMvc.perform(post("/batidas")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(new MomentoModel("2018-08-22T18:00:00"))))
                .andDo(print())
                .andExpect(status().isOk());
        this.mockMvc.perform(post("/alocacoes")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(new AlocacaoModel("2018-08-22","PT2H30M0S","ACME Corporation" ))))
                .andDo(print())
                .andExpect(status().isOk());
    }


    @Test
    public void NAO_PODE_ALOCAR_TEMPO_MAIOR_QUE_O_TEMPO_TRABALHADO_NO_DIA_BAD_REQUEST() throws Exception {
        this.mockMvc.perform(post("/batidas")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(new MomentoModel("2018-08-22T08:00:00"))))
                .andDo(print())
                .andExpect(status().isOk());
        this.mockMvc.perform(post("/batidas")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(new MomentoModel("2018-08-22T11:00:00"))))
                .andDo(print())
                .andExpect(status().isOk());
        this.mockMvc.perform(post("/batidas")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(new MomentoModel("2018-08-22T12:00:00"))))
                .andDo(print())
                .andExpect(status().isOk());
        this.mockMvc.perform(post("/batidas")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(new MomentoModel("2018-08-22T18:00:00"))))
                .andDo(print())
                .andExpect(status().isOk());
        this.mockMvc.perform(post("/alocacoes")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(new AlocacaoModel("2018-08-22","PT2H30M0S","ACME Corporation" ))))
                .andDo(print())
                .andExpect(status().isOk());
        this.mockMvc.perform(post("/alocacoes")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(new AlocacaoModel("2018-08-22","PT9H30M0S","ACME Corporation" ))))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().json(objectMapper.writeValueAsString(Errors
                        .Alocacoes.Alocar.NAO_PODE_ALOCAR_TEMPO_MAIOR_QUE_O_TEMPO_TRABALHADO_NO_DIA
                        .getException()
                        .getMensagem())
                ));
    }

}
