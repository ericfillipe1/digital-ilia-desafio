package digital.ilia.desafio.service;

import digital.ilia.desafio.entity.PontoEntity;
import digital.ilia.desafio.exception.Errors;
import digital.ilia.desafio.exception.GlobalException;
import digital.ilia.desafio.model.MomentoModel;
import digital.ilia.desafio.repository.PontoRepository;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;

@Service
@NoArgsConstructor
public class PontoService {
    @Autowired
    public void setPontoRepository(PontoRepository pontoRepository) {
        this.pontoRepository = pontoRepository;
    }

    private PontoRepository pontoRepository;
    public void baterPonto(MomentoModel momento) throws GlobalException, ParseException {

        var dataHora= new SimpleDateFormat( "yyyy-MM-dd'T'HH:mm:ss")
                .parse(momento.getDataHora());


        var list =pontoRepository.findByDataHora(dataHora);

        if (list.size() > 0){
                throw Errors.Batidas.BaterPonto.HORARIOS_JA_REGISTRADO.getException();
        }
        var day= new DateTime(dataHora).getDayOfWeek();
        if (day ==  DateTimeConstants.SATURDAY || day ==  DateTimeConstants.SUNDAY ){
            throw Errors.Batidas.BaterPonto.SABADO_E_DOMINGO_NAO_SAO_PERMITIDOS_COMO_DIA_DE_TRABALHO.getException();
        }
        var  startDate = new DateTime(dataHora)
                .withTime(0, 0, 0, 0)
                .toDate();
        var  endDate = new DateTime(dataHora)
                .withTime(23, 59, 59, 999)
                .toDate();
        list =pontoRepository.findByDataHoraBetween(startDate,endDate);


        var e = new PontoEntity();
        e.setDataHora(dataHora);

        list.add(e);
        list.sort((d1,d2) -> d1.getDataHora().compareTo(d2.getDataHora()));


        if (list.size() >= 3){
            var HorarioDeAlmocoInicial =list.get(1).getDataHora();
            var HorarioDeAlmocoDesejado = new DateTime(list.get(1).getDataHora()).plusHours(1).toDate();
            var HorarioDeAlmocoFinal =list.get(2).getDataHora();
            if (HorarioDeAlmocoDesejado.after(HorarioDeAlmocoFinal)){
                throw Errors.Batidas.BaterPonto.DEVE_HAVER_NO_MÍNIMO_1_HORA_DE_ALMOCO.getException();
            }
        }
        if (list.size() > 4){
            throw Errors.Batidas.BaterPonto.APENAS_4_HORARIOS_PODEM_SER_REGISTRADOS_POR_DIA.getException();
        }
        pontoRepository.save(e);
        pontoRepository.flush();

    }
}
