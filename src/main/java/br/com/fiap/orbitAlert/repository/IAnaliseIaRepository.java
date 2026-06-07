package br.com.fiap.orbitAlert.repository;

import br.com.fiap.orbitAlert.model.AnaliseIa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface IAnaliseIaRepository extends JpaRepository<AnaliseIa, Long> {

    Optional<AnaliseIa> findByAlertaId(Long idAlerta);

    List<AnaliseIa> findByDsModeloIa(String dsModeloIa);

    // JPQL — busca análises de alertas ativos
    @Query("SELECT ai FROM AnaliseIa ai " +
            "WHERE ai.alerta.stStatus IN ('ATIVO', 'EM_ATENDIMENTO') " +
            "ORDER BY ai.dtGeracao DESC")
    List<AnaliseIa> buscarAnalisesDeSAlertasAtivos();

    // Native — busca total de tokens consumidos por modelo
    @Query(nativeQuery = true, value =
            "SELECT DS_MODELO_IA, SUM(NR_TOKEN_USADOS) AS TOTAL_TOKENS " +
                    "FROM TB_ANALISE_IA " +
                    "GROUP BY DS_MODELO_IA " +
                    "ORDER BY TOTAL_TOKENS DESC")
    List<Object[]> buscarConsumoTokensPorModelo();
}