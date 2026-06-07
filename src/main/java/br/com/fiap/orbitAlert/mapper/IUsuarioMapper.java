package br.com.fiap.orbitAlert.mapper;

import br.com.fiap.orbitAlert.dto.UsuarioDTO;
import br.com.fiap.orbitAlert.model.Usuario;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IUsuarioMapper {

    UsuarioDTO toDTO(Usuario entity);
    Usuario toEntity(UsuarioDTO dto);
}