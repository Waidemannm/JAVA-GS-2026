package br.com.fiap.orbitAlert.mapper;

import br.com.fiap.orbitAlert.dto.NotificacaoDTO;
import br.com.fiap.orbitAlert.model.Notificacao;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface INotificacaoMapper {

    NotificacaoDTO toDTO(Notificacao entity);
    Notificacao toEntity(NotificacaoDTO dto);
}