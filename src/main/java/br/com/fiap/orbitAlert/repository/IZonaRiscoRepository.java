package br.com.fiap.orbitAlert.repository;

import br.com.fiap.orbitAlert.model.ZonaRisco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IZonaRiscoRepository extends JpaRepository<ZonaRisco, Long> {

    List<ZonaRisco> findByMunicipioId(Long idMunicipio);

    List<ZonaRisco> findByStAtivo(String stAtivo);

    // JPQL — busca zonas ativas com limiar menor ou igual ao índice informado
    @Query("SELECT z FROM ZonaRisco z " +
            "WHERE z.nrLimiarAlerta <= :indice " +
            "AND z.stAtivo = 'S'")
    List<ZonaRisco> buscarZonasComLimiarAtingido(Integer indice);

    // Native — busca zonas de um município com alertas ativos
    @Query(nativeQuery = true, value =
            "SELECT DISTINCT z.* FROM TB_ZONA_RISCO z " +
                    "JOIN TB_ALERTA a ON a.ID_ZONA = z.ID_ZONA " +
                    "WHERE z.ID_MUNICIPIO = :idMunicipio " +
                    "AND a.ST_STATUS IN ('ATIVO', 'EM_ATENDIMENTO')")
    List<ZonaRisco> buscarZonasComAlertasAtivosPorMunicipio(Long idMunicipio);
}