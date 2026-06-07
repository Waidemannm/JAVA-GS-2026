package br.com.fiap.orbitAlert.mapper;

import br.com.fiap.orbitAlert.dto.TipoAlertaDTO;
import br.com.fiap.orbitAlert.model.TipoAlerta;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-06-02T23:24:30-0300",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.10 (Oracle Corporation)"
)
@Component
public class ITipoAlertaMapperImpl implements ITipoAlertaMapper {

    @Override
    public TipoAlertaDTO toDTO(TipoAlerta entity) {
        if ( entity == null ) {
            return null;
        }

        TipoAlertaDTO tipoAlertaDTO = new TipoAlertaDTO();

        tipoAlertaDTO.setNmTipo( entity.getNmTipo() );
        tipoAlertaDTO.setDsDescricao( entity.getDsDescricao() );

        return tipoAlertaDTO;
    }

    @Override
    public TipoAlerta toEntity(TipoAlertaDTO dto) {
        if ( dto == null ) {
            return null;
        }

        TipoAlerta tipoAlerta = new TipoAlerta();

        tipoAlerta.setNmTipo( dto.getNmTipo() );
        tipoAlerta.setDsDescricao( dto.getDsDescricao() );

        return tipoAlerta;
    }
}
