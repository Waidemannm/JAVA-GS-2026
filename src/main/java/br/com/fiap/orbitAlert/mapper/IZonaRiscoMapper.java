package br.com.fiap.orbitAlert.mapper;

import br.com.fiap.orbitAlert.dto.ZonaRiscoDTO;
import br.com.fiap.orbitAlert.model.ZonaRisco;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IZonaRiscoMapper {

    ZonaRiscoDTO toDTO(ZonaRisco entity);
    ZonaRisco toEntity(ZonaRiscoDTO dto);
}