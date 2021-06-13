package digital.ilia.desafio.repository;

import digital.ilia.desafio.entity.PontoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.xml.crypto.Data;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public interface PontoRepository extends  JpaRepository<PontoEntity, UUID> {

    List<PontoEntity> findByDataHoraBetween(Date startDate , Date endDate);

    List<PontoEntity> findByDataHora(Date dataHora);
}
