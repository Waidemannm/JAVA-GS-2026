package br.com.fiap.orbitAlert.mapper;

import br.com.fiap.orbitAlert.dto.AnaliseIaDTO;
import br.com.fiap.orbitAlert.model.AnaliseIa;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IAnaliseIaMapper {

    AnaliseIaDTO toDTO(AnaliseIa entity);
    AnaliseIa toEntity(AnaliseIaDTO dto);
}