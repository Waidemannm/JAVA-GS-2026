package br.com.fiap.orbitAlert.mapper;

import br.com.fiap.orbitAlert.dto.EstacaoIotDTO;
import br.com.fiap.orbitAlert.model.EstacaoIot;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IEstacaoIotMapper {

    EstacaoIotDTO toDTO(EstacaoIot entity);
    EstacaoIot toEntity(EstacaoIotDTO dto);
}