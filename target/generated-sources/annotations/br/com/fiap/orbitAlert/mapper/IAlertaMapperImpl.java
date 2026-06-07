package br.com.fiap.orbitAlert.mapper;

import br.com.fiap.orbitAlert.dto.AlertaDTO;
import br.com.fiap.orbitAlert.model.Alerta;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-06-02T23:24:30-0300",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.10 (Oracle Corporation)"
)
@Component
public class IAlertaMapperImpl implements IAlertaMapper {

    @Override
    public AlertaDTO toDTO(Alerta entity) {
        if ( entity == null ) {
            return null;
        }

        AlertaDTO alertaDTO = new AlertaDTO();

        alertaDTO.setNrNivelRisco( entity.getNrNivelRisco() );
        alertaDTO.setStStatus( entity.getStStatus() );
        alertaDTO.setDsObservacao( entity.getDsObservacao() );
        alertaDTO.setDtFechamento( entity.getDtFechamento() );
        alertaDTO.setZonaRisco( entity.getZonaRisco() );
        alertaDTO.setTipoAlerta( entity.getTipoAlerta() );

        return alertaDTO;
    }

    @Override
    public Alerta toEntity(AlertaDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Alerta alerta = new Alerta();

        alerta.setNrNivelRisco( dto.getNrNivelRisco() );
        alerta.setStStatus( dto.getStStatus() );
        alerta.setDsObservacao( dto.getDsObservacao() );
        alerta.setDtFechamento( dto.getDtFechamento() );
        alerta.setZonaRisco( dto.getZonaRisco() );
        alerta.setTipoAlerta( dto.getTipoAlerta() );

        return alerta;
    }
}
