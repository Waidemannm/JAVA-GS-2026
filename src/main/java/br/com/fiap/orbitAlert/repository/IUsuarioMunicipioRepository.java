package br.com.fiap.orbitAlert.repository;

import br.com.fiap.orbitAlert.model.UsuarioMunicipio;
import br.com.fiap.orbitAlert.model.UsuarioMunicipioPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IUsuarioMunicipioRepository extends JpaRepository<UsuarioMunicipio, UsuarioMunicipioPK> {

    List<UsuarioMunicipio> findByUsuarioId(Long idUsuario);

    List<UsuarioMunicipio> findByMunicipioId(Long idMunicipio);

    // JPQL — busca todos os vínculos de um usuário com os municípios ativos
    @Query("SELECT um FROM UsuarioMunicipio um " +
            "WHERE um.usuario.id = :idUsuario " +
            "AND um.municipio.stAtivo = 'S'")
    List<UsuarioMunicipio> buscarMunicipiosAtivosPorUsuario(Long idUsuario);
}