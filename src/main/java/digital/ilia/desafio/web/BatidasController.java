/*
 *   Copyright (c) 2021
 *   All rights reserved.
 */
package digital.ilia.desafio.web;

import digital.ilia.desafio.exception.GlobalException;
import digital.ilia.desafio.service.PontoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import digital.ilia.desafio.model.MensagemModel;
import digital.ilia.desafio.model.MomentoModel;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;

import javax.validation.Valid;
import java.text.ParseException;

@RestController
@Tag(name = "Batidas")
@RequestMapping("/batidas")
public class BatidasController {

        private PontoService pontoService;

        @Autowired
        public void setPontoService(PontoService pontoService) {
                this.pontoService = pontoService;
        }

        @Operation(
                summary = "Bater ponto",
                description = "Registrar um horário da jornada diária de trabalho",
                responses = {
                        @ApiResponse(description = "Created", responseCode = "201"),
                        @ApiResponse(
                                responseCode = "400",
                                description = "Bad Request",
                                content = @Content(
                                        mediaType = "application/json",
                                        schema = @Schema(implementation = MensagemModel.class),
                                        examples = {
                                                @ExampleObject(
                                                        name = "Data e hora em formato inválido",
                                                        value = "{\"mensagem\":\"Data e hora em formato inválido\"}"
                                                ),
                                                @ExampleObject(
                                                        name = "Campo obrigatório não informado",
                                                        value = "{\"mensagem\":\"Campo obrigatório não informado\"}")
                                        })
                        ),
                        @ApiResponse(
                                responseCode = "409",
                                description = "Conflict",
                                content = @Content(
                                        mediaType = "application/json",
                                        schema = @Schema(implementation = MensagemModel.class),
                                        examples = {
                                                @ExampleObject(
                                                        name = "Horários já registrado",
                                                        value = "{\"mensagem\":\"Horários já registrado\"}"
                                                )
                                        })
                        ),
                        @ApiResponse(
                                responseCode = "403",
                                description = "Forbiden",
                                content = @Content(
                                        mediaType = "application/json",
                                        schema = @Schema(implementation = MensagemModel.class),
                                        examples = {
                                                @ExampleObject(
                                                        name = "apenas 4 horários por dia",
                                                        value = "{\"mensagem\":\"Apenas 4 horários podem ser registrados por dia\"}"
                                                ),
                                                @ExampleObject(
                                                        name = "mínimo 1 hora de almoço",
                                                        value = "{\"mensagem\":\"Deve haver no mínimo 1 hora de almoço\"}"
                                                ),
                                                @ExampleObject(
                                                        name = "sábado e domingo não são permitidos",
                                                        value = "{\"mensagem\":\"Sábado e domingo não são permitidos como dia de trabalho\"}")
                                        }))
                },
                requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true)
        )
        @RequestMapping(value = "", method = RequestMethod.POST, produces = "application/json")
        public void baterPonto(@Valid @RequestBody() MomentoModel momento) throws GlobalException, ParseException {
                pontoService.baterPonto(momento);
        }

}