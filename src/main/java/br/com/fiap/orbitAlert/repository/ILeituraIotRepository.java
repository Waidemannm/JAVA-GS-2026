package br.com.fiap.orbitAlert.repository;

import br.com.fiap.orbitAlert.model.LeituraIot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ILeituraIotRepository extends JpaRepository<LeituraIot, Long> {

    List<LeituraIot> findByEstacaoIotId(Long idEstacao);

    List<LeituraIot> findByNrIndiceRiscoGreaterThanEqual(Integer indice);

    // JPQL — busca última leitura de cada estação de uma zona
    @Query("SELECT l FROM LeituraIot l " +
            "WHERE l.estacaoIot.zonaRisco.id = :idZona " +
            "ORDER BY l.dtLeitura DESC")
    List<LeituraIot> buscarLeiturasPorZona(Long idZona);

    // Native — busca última leitura de uma estação
    @Query(nativeQuery = true, value =
            "SELECT * FROM TB_LEITURA_IOT " +
                    "WHERE ID_ESTACAO = :idEstacao " +
                    "ORDER BY DT_LEITURA DESC " +
                    "FETCH FIRST 1 ROWS ONLY")
    LeituraIot buscarUltimaLeituraPorEstacao(Long idEstacao);
}