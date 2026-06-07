package br.com.fiap.orbitAlert.mapper;

import br.com.fiap.orbitAlert.dto.UsuarioMunicipioDTO;
import br.com.fiap.orbitAlert.model.UsuarioMunicipio;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-06-02T23:24:30-0300",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.10 (Oracle Corporation)"
)
@Component
public class IUsuarioMunicipioMapperImpl implements IUsuarioMunicipioMapper {

    @Override
    public UsuarioMunicipioDTO toDTO(UsuarioMunicipio entity) {
        if ( entity == null ) {
            return null;
        }

        UsuarioMunicipioDTO usuarioMunicipioDTO = new UsuarioMunicipioDTO();

        usuarioMunicipioDTO.setUsuario( entity.getUsuario() );
        usuarioMunicipioDTO.setMunicipio( entity.getMunicipio() );

        return usuarioMunicipioDTO;
    }

    @Override
    public UsuarioMunicipio toEntity(UsuarioMunicipioDTO dto) {
        if ( dto == null ) {
            return null;
        }

        UsuarioMunicipio usuarioMunicipio = new UsuarioMunicipio();

        usuarioMunicipio.setUsuario( dto.getUsuario() );
        usuarioMunicipio.setMunicipio( dto.getMunicipio() );

        return usuarioMunicipio;
    }
}
