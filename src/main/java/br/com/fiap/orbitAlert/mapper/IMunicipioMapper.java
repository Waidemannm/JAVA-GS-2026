package br.com.fiap.orbitAlert.mapper;

import br.com.fiap.orbitAlert.dto.MunicipioDTO;
import br.com.fiap.orbitAlert.model.Municipio;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IMunicipioMapper {

    MunicipioDTO toDTO(Municipio entity);
    Municipio toEntity(MunicipioDTO dto);
}