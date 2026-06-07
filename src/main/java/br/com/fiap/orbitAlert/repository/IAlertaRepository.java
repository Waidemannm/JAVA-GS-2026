package br.com.fiap.orbitAlert.repository;

import br.com.fiap.orbitAlert.model.Alerta;
import br.com.fiap.orbitAlert.model.enums.StatusAlertaEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IAlertaRepository extends JpaRepository<Alerta, Long> {

    List<Alerta> findByStStatus(StatusAlertaEnum stStatus);

    List<Alerta> findByZonaRiscoId(Long idZona);

    // JPQL — busca alertas ativos de um município ordenados por nível de risco
    @Query("SELECT a FROM Alerta a " +
            "WHERE a.zonaRisco.municipio.id = :idMunicipio " +
            "AND a.stStatus IN ('ATIVO', 'EM_ATENDIMENTO') " +
            "ORDER BY a.nrNivelRisco DESC")
    List<Alerta> buscarAtivosAgrupadosPorMunicipio(Long idMunicipio);

    // Native — busca alertas críticos (nível >= 4) nos últimos 30 dias
    @Query(nativeQuery = true, value =
            "SELECT * FROM TB_ALERTA " +
                    "WHERE NR_NIVEL_RISCO >= 4 " +
                    "AND DT_CRIACAO >= SYSDATE - 30 " +
                    "ORDER BY NR_NIVEL_RISCO DESC, DT_CRIACAO DESC")
    List<Alerta> buscarCriticosUltimos30Dias();
}