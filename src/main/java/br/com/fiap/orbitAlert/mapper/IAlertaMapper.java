package br.com.fiap.orbitAlert.mapper;

import br.com.fiap.orbitAlert.dto.AlertaDTO;
import br.com.fiap.orbitAlert.model.Alerta;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IAlertaMapper {

    AlertaDTO toDTO(Alerta entity);
    Alerta toEntity(AlertaDTO dto);
}