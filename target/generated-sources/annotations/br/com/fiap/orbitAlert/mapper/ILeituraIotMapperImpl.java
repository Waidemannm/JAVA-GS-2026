package br.com.fiap.orbitAlert.mapper;

import br.com.fiap.orbitAlert.dto.LeituraIotDTO;
import br.com.fiap.orbitAlert.model.LeituraIot;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-06-02T23:24:30-0300",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.10 (Oracle Corporation)"
)
@Component
public class ILeituraIotMapperImpl implements ILeituraIotMapper {

    @Override
    public LeituraIotDTO toDTO(LeituraIot entity) {
        if ( entity == null ) {
            return null;
        }

        LeituraIotDTO leituraIotDTO = new LeituraIotDTO();

        leituraIotDTO.setNrTemperatura( entity.getNrTemperatura() );
        leituraIotDTO.setNrUmidade( entity.getNrUmidade() );
        leituraIotDTO.setNrChuvaMm( entity.getNrChuvaMm() );
        leituraIotDTO.setNrIndiceRisco( entity.getNrIndiceRisco() );
        leituraIotDTO.setEstacaoIot( entity.getEstacaoIot() );

        return leituraIotDTO;
    }

    @Override
    public LeituraIot toEntity(LeituraIotDTO dto) {
        if ( dto == null ) {
            return null;
        }

        LeituraIot leituraIot = new LeituraIot();

        leituraIot.setNrTemperatura( dto.getNrTemperatura() );
        leituraIot.setNrUmidade( dto.getNrUmidade() );
        leituraIot.setNrChuvaMm( dto.getNrChuvaMm() );
        leituraIot.setNrIndiceRisco( dto.getNrIndiceRisco() );
        leituraIot.setEstacaoIot( dto.getEstacaoIot() );

        return leituraIot;
    }
}
