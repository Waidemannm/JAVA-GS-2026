package br.com.fiap.orbitAlert.mapper;

import br.com.fiap.orbitAlert.dto.TipoAlertaDTO;
import br.com.fiap.orbitAlert.model.TipoAlerta;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface ITipoAlertaMapper {

    TipoAlertaDTO toDTO(TipoAlerta entity);
    TipoAlerta toEntity(TipoAlertaDTO dto);
}