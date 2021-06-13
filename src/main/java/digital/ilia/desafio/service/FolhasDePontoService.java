package digital.ilia.desafio.service;

import digital.ilia.desafio.entity.AlocacaoEntity;
import digital.ilia.desafio.entity.PontoEntity;
import digital.ilia.desafio.exception.Errors;
import digital.ilia.desafio.exception.GlobalException;
import digital.ilia.desafio.model.AlocacaoModel;
import digital.ilia.desafio.model.RelatorioAlocacoesModel;
import digital.ilia.desafio.model.RelatorioModel;
import digital.ilia.desafio.model.RelatorioRegistrosModel;
import digital.ilia.desafio.repository.AlocacaoRepository;
import digital.ilia.desafio.repository.PontoRepository;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.joda.time.Interval;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

@Service
@NoArgsConstructor
public class FolhasDePontoService {
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
    public RelatorioModel relatorioMensal(String strMes) throws ParseException, GlobalException {
        var mes= new SimpleDateFormat( "yyyy-MM").parse(strMes);
        var primeiroDiaDoMes = new DateTime(mes).dayOfMonth().withMinimumValue().toDate();
        var proximo = new DateTime(mes).dayOfMonth().withMinimumValue();
        var ultimoDiaDoMes = new DateTime(mes).dayOfMonth().withMaximumValue().withTime(23, 59, 59, 999).toDate();
        var pontolista= pontoRepository.findByDataHoraBetween(primeiroDiaDoMes,ultimoDiaDoMes);
        var alocacaolista= alocacaoRepository.findByDiaBetween(primeiroDiaDoMes,ultimoDiaDoMes);
        if (!(pontolista.size()> 0 ||alocacaolista.size()> 0) ){
            throw Errors.FolhasDePonto.RelatorioMensal.RELATORIO_NAO_ENCONTRADO.getException();
        }
        var relatorioModel = new RelatorioModel();
        relatorioModel.setAlocacoes(new ArrayList<>());
        relatorioModel.setRegistros(new ArrayList<>());
        relatorioModel.setMes(strMes);
        var horasTRabalhadas = Duration.ofHours(0);
        var cargaHoraria = Duration.ofHours(0);
        while(proximo.toDate().before(ultimoDiaDoMes)){
            var dia= new SimpleDateFormat( "yyyy-MM-dd").format(mes);
            var  startDate = new DateTime(proximo)
                    .withTime(0, 0, 0, 0)
                    .toDate();
            var day= new DateTime(startDate).getDayOfWeek();
            if (!(day ==  DateTimeConstants.SATURDAY || day ==  DateTimeConstants.SUNDAY )){
                var  endDate = new DateTime(proximo)
                        .withTime(23, 59, 59, 999)
                        .toDate();
                var interval =new Interval(new DateTime(startDate),new DateTime(endDate));
                var _pontolista =pontolista.stream()
                        .filter(ponto ->
                                interval.contains(new DateTime(ponto.getDataHora())))
                        .collect(Collectors.toList());

                var tempoTrabalhado = Duration.ofHours(0);
                cargaHoraria =cargaHoraria.plusHours(8);
                if ( _pontolista.size()> 0){

                    if (_pontolista.size() >=2){
                        var seconds =new DateTime(_pontolista.get(1).getDataHora()).getSecondOfDay() - new DateTime(_pontolista.get(0).getDataHora()).getSecondOfDay();
                        tempoTrabalhado =tempoTrabalhado.plusSeconds(seconds);
                    }
                    if (_pontolista.size() >=4){
                        var seconds =new DateTime(_pontolista.get(3).getDataHora()).getSecondOfDay() - new DateTime(_pontolista.get(2).getDataHora()).getSecondOfDay();
                        tempoTrabalhado=tempoTrabalhado.plusSeconds(seconds);
                    }
                    horasTRabalhadas=horasTRabalhadas.plusSeconds(tempoTrabalhado.toSeconds());
                    var relatorioAlocacoesModel = new RelatorioRegistrosModel();
                    relatorioAlocacoesModel.setDia(dia);
                    List<String> Horarios = new ArrayList<>();
                    for (PontoEntity _ponto:_pontolista) {
                        var horario= new SimpleDateFormat( "HH:mm:ss").format(_ponto.getDataHora());
                        Horarios.add(horario);
                    }
                    relatorioAlocacoesModel.setHorarios(Horarios);
                    relatorioModel.getRegistros().add(relatorioAlocacoesModel);
                }
            }
            proximo =proximo.plusDays(1);
        }
        if (horasTRabalhadas.getSeconds() > cargaHoraria.getSeconds()){
            relatorioModel.setHorasExcedentes(horasTRabalhadas.toString());
        }else {

            relatorioModel.setHorasExcedentes(Duration.ofHours(0).toString());
        }

        if (cargaHoraria.getSeconds() > horasTRabalhadas.getSeconds()){
            relatorioModel.setHorasDevidas(cargaHoraria.toString());
        }else {

            relatorioModel.setHorasDevidas(Duration.ofHours(0).toString());
        }
        relatorioModel.setHorasTrabalhadas(horasTRabalhadas.toString());
        Map<String, List<AlocacaoEntity>> AlocacaoMap = alocacaolista.stream()
                .collect(groupingBy(a -> a.getNomeProjeto()));
        for(String key : AlocacaoMap.keySet()){
            var relatorioAlocacoesModel = new RelatorioAlocacoesModel();
            var values =AlocacaoMap.get(key);
            var tempo = Duration.ofHours(0);
            for(AlocacaoEntity alocacaoEntity :values){
                tempo= tempo.plusSeconds(alocacaoEntity.getTempo().toSeconds());
            }
            relatorioAlocacoesModel.setNomeProjeto(key);
            relatorioAlocacoesModel.setTempo(tempo.toString());
            relatorioModel.getAlocacoes().add(relatorioAlocacoesModel);
        }
        return relatorioModel;
    }

    List<Date> getAllDateOfMonth( int year, int  month) {
        var yearMonth= YearMonth.of(year, month);
        var firstDayOfTheMonth = yearMonth.atDay(1);
        var datesOfThisMonth = new ArrayList<Date>();
        return datesOfThisMonth;
    }
}
