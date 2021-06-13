/*
 *   Copyright (c) 2021 
 *   All rights reserved.
 */
package digital.ilia.desafio.service;

import digital.ilia.desafio.entity.AlocacaoEntity;
import digital.ilia.desafio.exception.Errors;
import digital.ilia.desafio.exception.GlobalException;
import digital.ilia.desafio.model.AlocacaoModel;
import digital.ilia.desafio.repository.AlocacaoRepository;

import digital.ilia.desafio.repository.PontoRepository;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;

@Service
@NoArgsConstructor
public class AlocacaoService {

    @Autowired
    public void setPontoRepository(PontoRepository pontoRepository) {
        this.pontoRepository = pontoRepository;
    }


    @Autowired
    public void setAlocacaoRepository(AlocacaoRepository alocacaoRepository) {
        this.alocacaoRepository = alocacaoRepository;
    }

    private PontoRepository pontoRepository;
    private AlocacaoRepository alocacaoRepository;

    public void alocar(AlocacaoModel alocacao) throws ParseException, GlobalException {

        var dia= new SimpleDateFormat( "yyyy-MM-dd")
                .parse(alocacao.getDia());
        var  startDate = new DateTime(dia)
                .withTime(0, 0, 0, 0)
                .toDate();
        var  endDate = new DateTime(dia)
                .withTime(23, 59, 59, 999)
                .toDate();
        var tempo = Duration.parse(alocacao.getTempo());
        var tempoTrabalhado = Duration.ofHours(0);
        var tempoAlocado = Duration.parse(alocacao.getTempo());
        var lista =pontoRepository.findByDataHoraBetween(startDate,endDate);
        lista.sort((d1,d2) -> d1.getDataHora().compareTo(d2.getDataHora()));
        if (lista.size() >=2){
            var seconds =new DateTime(lista.get(1).getDataHora()).getSecondOfDay() - new DateTime(lista.get(0).getDataHora()).getSecondOfDay();
            tempoTrabalhado =tempoTrabalhado.plusSeconds(seconds);
        }
        if (lista.size() >=4){
            var seconds =new DateTime(lista.get(3).getDataHora()).getSecondOfDay() - new DateTime(lista.get(2).getDataHora()).getSecondOfDay();
            tempoTrabalhado=tempoTrabalhado.plusSeconds(seconds);
        }

        var alocacaoLista =alocacaoRepository.findByDia(dia);
        for (AlocacaoEntity alocacaoEnitity :alocacaoLista) {
            tempoAlocado =tempoAlocado.plusSeconds(alocacaoEnitity.getTempo().toSeconds());
        }
        if (tempoTrabalhado.toSeconds() >= tempoAlocado.toSeconds()){
            AlocacaoEntity alocacaoEntity = new AlocacaoEntity();
            alocacaoEntity.setDia(dia);
            alocacaoEntity.setNomeProjeto(alocacao.getNomeProjeto());
            alocacaoEntity.setTempo(tempo);
            alocacaoRepository.save(alocacaoEntity);
            alocacaoRepository.flush();

        }else {

            throw Errors.Alocacoes.Alocar.NAO_PODE_ALOCAR_TEMPO_MAIOR_QUE_O_TEMPO_TRABALHADO_NO_DIA.getException();
        }
    }
}
