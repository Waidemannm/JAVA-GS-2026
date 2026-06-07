package br.com.fiap.orbitAlert.repository;

import br.com.fiap.orbitAlert.model.HistoricoAlerta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IHistoricoAlertaRepository extends JpaRepository<HistoricoAlerta, Long> {

    List<HistoricoAlerta> findByAlertaId(Long idAlerta);

    List<HistoricoAlerta> findByStStatusNovo(String stStatusNovo);

    // JPQL — busca histórico completo de um alerta ordenado por data
    @Query("SELECT h FROM HistoricoAlerta h " +
            "WHERE h.alerta.id = :idAlerta " +
            "ORDER BY h.dtAlteracao ASC")
    List<HistoricoAlerta> buscarHistoricoOrdenadoPorAlerta(Long idAlerta);

    // Native — busca último histórico de cada alerta ativo
    @Query(nativeQuery = true, value =
            "SELECT h.* FROM TB_HISTORICO_ALERTA h " +
                    "JOIN TB_ALERTA a ON a.ID_ALERTA = h.ID_ALERTA " +
                    "WHERE a.ST_STATUS = 'ATIVO' " +
                    "AND h.DT_ALTERACAO = (" +
                    "   SELECT MAX(h2.DT_ALTERACAO) FROM TB_HISTORICO_ALERTA h2 " +
                    "   WHERE h2.ID_ALERTA = h.ID_ALERTA)")
    List<HistoricoAlerta> buscarUltimoHistoricoPorAlertaAtivo();
}