/*
 *   Copyright (c) 2021 
 *   All rights reserved.
 */
package digital.ilia.desafio.web;

import digital.ilia.desafio.exception.GlobalException;
import digital.ilia.desafio.model.RelatorioModel;
import digital.ilia.desafio.service.FolhasDePontoService;
import digital.ilia.desafio.validator.IsDateFormat;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.text.ParseException;

@RestController
@Tag(name = "Folhas de ponto")
@RequestMapping("/folhas-de-ponto")
public class FolhasDePontoController {

    private FolhasDePontoService folhasDePontoService;

    @Autowired
    public void setFolhasDePontoService(FolhasDePontoService folhasDePontoService) {
        this.folhasDePontoService = folhasDePontoService;
    }

    @Operation(
            summary = "Relatório mensal",
            description = "Registrar um horário da jornada diária de trabalho",
            responses = {
                    @ApiResponse(
                            description = "Horas alocadas ao projeto",
                            responseCode = "200",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            implementation = RelatorioModel.class
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Relatório não encontrado"
                    )
            })
    @RequestMapping(value = "/{mes}", method = RequestMethod.GET, produces = "application/json")
    @Parameter(in = ParameterIn.PATH, name = "mes", content = @Content(schema = @Schema(type = "string", example = "2018-08")))
    public RelatorioModel relatorioMensal(
            @IsDateFormat(message = "mes formato inválido", pattern = "yyyy-MM")
            @PathVariable("mes") String mes
    ) throws GlobalException, ParseException {
        return folhasDePontoService.relatorioMensal(mes);
    }

}