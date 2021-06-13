/*
 *   Copyright (c) 2021 
 *   All rights reserved.
 */
package digital.ilia.desafio.web;

import digital.ilia.desafio.exception.GlobalException;
import digital.ilia.desafio.model.AlocacaoModel;
import digital.ilia.desafio.model.MensagemModel;
import digital.ilia.desafio.service.AlocacaoService;
import io.swagger.v3.oas.annotations.media.ExampleObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;

import javax.validation.Valid;
import java.text.ParseException;

@RestController
@Tag(name = "Alocacoes")
@RequestMapping("/alocacoes")
public class AlocacoesController {

        private AlocacaoService alocacaoService;

        @Autowired
        public void setAlocacaoService(AlocacaoService alocacaoService) {
                this.alocacaoService = alocacaoService;
        }

        @Operation(
                summary = "Alocar horas trabalhadas",
                description = "Alocar horas trabalhadas, de um dia de trabalho, em um projeto",
                responses = {
                        @ApiResponse(
                                description = "Horas alocadas ao projeto",
                                responseCode = "201"),
                        @ApiResponse(
                                responseCode = "400",
                                description = "Bad Request",
                                content = @Content(
                                        mediaType = "application/json",
                                        schema = @Schema(implementation = MensagemModel.class),
                                        examples = {
                                                @ExampleObject(
                                                        name = "Não pode alocar tempo maior que o tempo trabalhado no dia",
                                                        value = "{\"mensagem\":\"Não pode alocar tempo maior que o tempo trabalhado no dia\"}"
                                                )
                                        })
                        )
                },
                requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true)
        )
        @RequestMapping(value = "", method = RequestMethod.POST, produces = "application/json")

        public void alocar(@Valid @RequestBody() AlocacaoModel alocacao) throws ParseException, GlobalException {
                alocacaoService.alocar(alocacao);
        }

}