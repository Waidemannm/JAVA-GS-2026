package br.com.fiap.orbitAlert.mapper;

import br.com.fiap.orbitAlert.dto.HistoricoAlertaDTO;
import br.com.fiap.orbitAlert.model.HistoricoAlerta;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IHistoricoAlertaMapper {

    HistoricoAlertaDTO toDTO(HistoricoAlerta entity);
    HistoricoAlerta toEntity(HistoricoAlertaDTO dto);
}