package br.com.fiap.orbitAlert.mapper;

import br.com.fiap.orbitAlert.dto.HistoricoAlertaDTO;
import br.com.fiap.orbitAlert.model.HistoricoAlerta;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-06-02T23:24:30-0300",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.10 (Oracle Corporation)"
)
@Component
public class IHistoricoAlertaMapperImpl implements IHistoricoAlertaMapper {

    @Override
    public HistoricoAlertaDTO toDTO(HistoricoAlerta entity) {
        if ( entity == null ) {
            return null;
        }

        HistoricoAlertaDTO historicoAlertaDTO = new HistoricoAlertaDTO();

        historicoAlertaDTO.setStStatusAnt( entity.getStStatusAnt() );
        historicoAlertaDTO.setStStatusNovo( entity.getStStatusNovo() );
        historicoAlertaDTO.setNrIndiceRisco( entity.getNrIndiceRisco() );
        historicoAlertaDTO.setDsObservacao( entity.getDsObservacao() );
        historicoAlertaDTO.setNmUsuarioMod( entity.getNmUsuarioMod() );
        historicoAlertaDTO.setAlerta( entity.getAlerta() );

        return historicoAlertaDTO;
    }

    @Override
    public HistoricoAlerta toEntity(HistoricoAlertaDTO dto) {
        if ( dto == null ) {
            return null;
        }

        HistoricoAlerta historicoAlerta = new HistoricoAlerta();

        historicoAlerta.setStStatusAnt( dto.getStStatusAnt() );
        historicoAlerta.setStStatusNovo( dto.getStStatusNovo() );
        historicoAlerta.setNrIndiceRisco( dto.getNrIndiceRisco() );
        historicoAlerta.setDsObservacao( dto.getDsObservacao() );
        historicoAlerta.setNmUsuarioMod( dto.getNmUsuarioMod() );
        historicoAlerta.setAlerta( dto.getAlerta() );

        return historicoAlerta;
    }
}
