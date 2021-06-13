package digital.ilia.desafio.repository;

import digital.ilia.desafio.entity.AlocacaoEntity;
import digital.ilia.desafio.entity.PontoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public interface AlocacaoRepository extends  JpaRepository<AlocacaoEntity, UUID> {

    List<AlocacaoEntity> findByDia(Date dataHora);
    List<AlocacaoEntity> findByDiaBetween(Date startDate , Date endDate);
}
