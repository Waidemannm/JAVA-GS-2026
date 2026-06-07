package br.com.fiap.orbitAlert.repository;

import br.com.fiap.orbitAlert.model.Municipio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IMunicipioRepository extends JpaRepository<Municipio, Long> {

    List<Municipio> findByNmEstado(String nmEstado);

    List<Municipio> findByStAtivo(String stAtivo);

    // JPQL — busca municípios com alertas ativos
    @Query("SELECT DISTINCT m FROM Municipio m " +
            "JOIN m.zonas z " +
            "JOIN z.alertas a " +
            "WHERE a.stStatus IN ('ATIVO', 'EM_ATENDIMENTO')")
    List<Municipio> buscarComAlertasAtivos();

    // Native — busca municípios por nome ignorando maiúsculas
    @Query(nativeQuery = true, value =
            "SELECT * FROM TB_MUNICIPIO " +
                    "WHERE LOWER(NM_MUNICIPIO) LIKE LOWER(CONCAT('%', :nome, '%')) " +
                    "AND ST_ATIVO = 'S'")
    List<Municipio> buscarPorNome(String nome);
}