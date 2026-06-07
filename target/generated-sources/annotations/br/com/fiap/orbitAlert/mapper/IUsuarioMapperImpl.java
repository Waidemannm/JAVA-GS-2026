package br.com.fiap.orbitAlert.mapper;

import br.com.fiap.orbitAlert.dto.UsuarioDTO;
import br.com.fiap.orbitAlert.model.Usuario;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-06-02T23:24:30-0300",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.10 (Oracle Corporation)"
)
@Component
public class IUsuarioMapperImpl implements IUsuarioMapper {

    @Override
    public UsuarioDTO toDTO(Usuario entity) {
        if ( entity == null ) {
            return null;
        }

        UsuarioDTO usuarioDTO = new UsuarioDTO();

        usuarioDTO.setNmUsuario( entity.getNmUsuario() );
        usuarioDTO.setDsEmail( entity.getDsEmail() );
        usuarioDTO.setDsSenhaHash( entity.getDsSenhaHash() );
        usuarioDTO.setTpPerfil( entity.getTpPerfil() );
        usuarioDTO.setStAtivo( entity.getStAtivo() );

        return usuarioDTO;
    }

    @Override
    public Usuario toEntity(UsuarioDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Usuario usuario = new Usuario();

        usuario.setNmUsuario( dto.getNmUsuario() );
        usuario.setDsEmail( dto.getDsEmail() );
        usuario.setDsSenhaHash( dto.getDsSenhaHash() );
        usuario.setTpPerfil( dto.getTpPerfil() );
        usuario.setStAtivo( dto.getStAtivo() );

        return usuario;
    }
}
