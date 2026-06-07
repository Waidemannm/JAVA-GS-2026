package br.com.fiap.orbitAlert.mapper;

import br.com.fiap.orbitAlert.dto.LeituraIotDTO;
import br.com.fiap.orbitAlert.model.LeituraIot;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface ILeituraIotMapper {

    LeituraIotDTO toDTO(LeituraIot entity);
    LeituraIot toEntity(LeituraIotDTO dto);
}