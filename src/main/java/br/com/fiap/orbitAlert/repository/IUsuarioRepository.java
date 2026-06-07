package br.com.fiap.orbitAlert.repository;

import br.com.fiap.orbitAlert.model.Usuario;
import br.com.fiap.orbitAlert.model.enums.TipoPerfilEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface IUsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByDsEmail(String dsEmail);

    List<Usuario> findByTpPerfil(TipoPerfilEnum tpPerfil);

    // JPQL — busca usuários ativos vinculados a um município
    @Query("SELECT u FROM Usuario u " +
            "JOIN u.municipios um " +
            "WHERE um.municipio.id = :idMunicipio " +
            "AND u.stAtivo = 'S'")
    List<Usuario> buscarAtivosPorMunicipio(Long idMunicipio);

    // Native — busca usuário por e-mail ignorando maiúsculas/minúsculas
    @Query(nativeQuery = true, value =
            "SELECT * FROM TB_USUARIO " +
                    "WHERE LOWER(DS_EMAIL) = LOWER(:email) " +
                    "AND ST_ATIVO = 'S'")
    Optional<Usuario> buscarPorEmailAtivo(String email);
}