package br.com.fiap.orbitAlert.repository;

import br.com.fiap.orbitAlert.model.TipoAlerta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ITipoAlertaRepository extends JpaRepository<TipoAlerta, Long> {

    Optional<TipoAlerta> findByNmTipo(String nmTipo);

    List<TipoAlerta> findByNmTipoContainingIgnoreCase(String nmTipo);

    // Native — busca tipos com maior quantidade de alertas gerados
    @Query(nativeQuery = true, value =
            "SELECT t.* FROM TB_TIPO_ALERTA t " +
                    "JOIN TB_ALERTA a ON a.ID_TIPO_ALERTA = t.ID_TIPO_ALERTA " +
                    "GROUP BY t.ID_TIPO_ALERTA, t.NM_TIPO, t.DS_DESCRICAO " +
                    "ORDER BY COUNT(a.ID_ALERTA) DESC")
    List<TipoAlerta> buscarTiposPorFrequencia();
}