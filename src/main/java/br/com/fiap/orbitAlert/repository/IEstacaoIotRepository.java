package br.com.fiap.orbitAlert.repository;

import br.com.fiap.orbitAlert.model.EstacaoIot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IEstacaoIotRepository extends JpaRepository<EstacaoIot, Long> {

    List<EstacaoIot> findByZonaRiscoId(Long idZona);

    List<EstacaoIot> findByStAtivo(String stAtivo);

    // JPQL — busca estações ativas de um município
    @Query("SELECT e FROM EstacaoIot e " +
            "WHERE e.zonaRisco.municipio.id = :idMunicipio " +
            "AND e.stAtivo = 'S'")
    List<EstacaoIot> buscarAtivasPorMunicipio(Long idMunicipio);

    // Native — busca estações sem leitura nos últimos 7 dias
    @Query(nativeQuery = true, value =
            "SELECT e.* FROM TB_ESTACAO_IOT e " +
                    "WHERE e.ST_ATIVO = 'S' " +
                    "AND NOT EXISTS (" +
                    "   SELECT 1 FROM TB_LEITURA_IOT l " +
                    "   WHERE l.ID_ESTACAO = e.ID_ESTACAO " +
                    "   AND l.DT_LEITURA >= SYSDATE - 7)")
    List<EstacaoIot> buscarEstacoesSemLeituraRecente();
}