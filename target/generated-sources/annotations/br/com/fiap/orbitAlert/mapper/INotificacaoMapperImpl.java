package br.com.fiap.orbitAlert.mapper;

import br.com.fiap.orbitAlert.dto.NotificacaoDTO;
import br.com.fiap.orbitAlert.model.Notificacao;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-06-02T23:24:30-0300",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.10 (Oracle Corporation)"
)
@Component
public class INotificacaoMapperImpl implements INotificacaoMapper {

    @Override
    public NotificacaoDTO toDTO(Notificacao entity) {
        if ( entity == null ) {
            return null;
        }

        NotificacaoDTO notificacaoDTO = new NotificacaoDTO();

        notificacaoDTO.setTpNotificacao( entity.getTpNotificacao() );
        notificacaoDTO.setDsTitulo( entity.getDsTitulo() );
        notificacaoDTO.setDsMensagem( entity.getDsMensagem() );
        notificacaoDTO.setStLida( entity.getStLida() );
        notificacaoDTO.setUsuario( entity.getUsuario() );
        notificacaoDTO.setAlerta( entity.getAlerta() );

        return notificacaoDTO;
    }

    @Override
    public Notificacao toEntity(NotificacaoDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Notificacao notificacao = new Notificacao();

        notificacao.setTpNotificacao( dto.getTpNotificacao() );
        notificacao.setDsTitulo( dto.getDsTitulo() );
        notificacao.setDsMensagem( dto.getDsMensagem() );
        notificacao.setStLida( dto.getStLida() );
        notificacao.setUsuario( dto.getUsuario() );
        notificacao.setAlerta( dto.getAlerta() );

        return notificacao;
    }
}
