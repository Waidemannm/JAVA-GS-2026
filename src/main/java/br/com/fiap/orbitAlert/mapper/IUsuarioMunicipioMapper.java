package br.com.fiap.orbitAlert.mapper;

import br.com.fiap.orbitAlert.dto.UsuarioMunicipioDTO;
import br.com.fiap.orbitAlert.model.UsuarioMunicipio;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IUsuarioMunicipioMapper {

    UsuarioMunicipioDTO toDTO(UsuarioMunicipio entity);
    UsuarioMunicipio toEntity(UsuarioMunicipioDTO dto);
}