package digital.ilia.desafio;

import com.fasterxml.jackson.databind.ObjectMapper;
import digital.ilia.desafio.exception.Errors;
import digital.ilia.desafio.model.MensagemModel;
import digital.ilia.desafio.model.MomentoModel;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class BatidasTests {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Test
    public void CAMPO_OBRIGATORIO_NAO_INFORMADO_BadRequest() throws Exception {
        this.mockMvc.perform(post("/batidas")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(new MomentoModel(null))))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().json(objectMapper.writeValueAsString(new MensagemModel("Campo obrigatório não informado"))));
    }
    @Test
    public void DATA_E_HORA_EM_FORMATO_INVALIDO_BadRequest() throws Exception {
        this.mockMvc.perform(post("/batidas")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(new MomentoModel("banana"))))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().json(objectMapper.writeValueAsString(new MensagemModel("Data e hora em formato inválido"))));
    }

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
    }
    @Test
    public void DEVE_HAVER_NO_MÍNIMO_1_HORA_DE_ALMOCO_FORBIDDEN() throws Exception {
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
                .content(objectMapper.writeValueAsString(new MomentoModel("2018-08-22T11:50:00"))))
                .andDo(print())
                .andExpect(status().isForbidden())
                .andExpect(content().json(objectMapper.writeValueAsString(Errors
                        .Batidas.BaterPonto.DEVE_HAVER_NO_MÍNIMO_1_HORA_DE_ALMOCO
                        .getException()
                        .getMensagem())
                ));

    }
    @Test
    public void APENAS_4_HORARIOS_PODEM_SER_REGISTRADOS_POR_DIA_FORBIDDEN() throws Exception {

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
        this.mockMvc.perform(post("/batidas")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(new MomentoModel("2018-08-22T20:50:00"))))
                .andDo(print())
                .andExpect(status().isForbidden())
                .andExpect(content().json(objectMapper.writeValueAsString(Errors
                        .Batidas.BaterPonto.APENAS_4_HORARIOS_PODEM_SER_REGISTRADOS_POR_DIA
                        .getException()
                        .getMensagem())
                ));

    }

    @Test
    public void  SABADO_E_DOMINGO_NAO_SAO_PERMITIDOS_COMO_DIA_DE_TRABALHO_FORBIDDEN() throws Exception {

        this.mockMvc.perform(post("/batidas")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(new MomentoModel("2021-06-13T20:50:00"))))
                .andDo(print())
                .andExpect(status().isForbidden())
                .andExpect(content().json(objectMapper.writeValueAsString(Errors
                        .Batidas.BaterPonto.SABADO_E_DOMINGO_NAO_SAO_PERMITIDOS_COMO_DIA_DE_TRABALHO
                        .getException()
                        .getMensagem())
                ));

    }
    @Test
    public void  HORARIOS_JA_REGISTRADO_CONFLICT() throws Exception {
        this.mockMvc.perform(post("/batidas")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(new MomentoModel("2021-06-15T20:50:00"))))
                .andDo(print())
                .andExpect(status().isOk());
        this.mockMvc.perform(post("/batidas")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(new MomentoModel("2021-06-15T20:50:00"))))
                .andDo(print())
                .andExpect(status().isConflict())
                .andExpect(content().json(objectMapper.writeValueAsString(Errors
                        .Batidas.BaterPonto.HORARIOS_JA_REGISTRADO
                        .getException()
                        .getMensagem())
                ));

    }

}
