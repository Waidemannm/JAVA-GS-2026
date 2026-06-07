package br.com.fiap.orbitAlert.repository;

import br.com.fiap.orbitAlert.model.Notificacao;
import br.com.fiap.orbitAlert.model.enums.TipoNotificacaoEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface INotificacaoRepository extends JpaRepository<Notificacao, Long> {

    List<Notificacao> findByUsuarioId(Long idUsuario);

    List<Notificacao> findByTpNotificacao(TipoNotificacaoEnum tpNotificacao);

    // JPQL — busca notificações não lidas de um usuário
    @Query("SELECT n FROM Notificacao n " +
            "WHERE n.usuario.id = :idUsuario " +
            "AND n.stLida = 'N' " +
            "ORDER BY n.dtEnvio DESC")
    List<Notificacao> buscarNaoLidasPorUsuario(Long idUsuario);

    // Native — busca total de notificações não lidas por usuário
    @Query(nativeQuery = true, value =
            "SELECT COUNT(*) FROM TB_NOTIFICACAO " +
                    "WHERE ID_USUARIO = :idUsuario " +
                    "AND ST_LIDA = 'N'")
    Long contarNaoLidasPorUsuario(Long idUsuario);
}