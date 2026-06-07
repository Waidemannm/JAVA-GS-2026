package br.com.fiap.orbitAlert.mapper;

import br.com.fiap.orbitAlert.dto.EstacaoIotDTO;
import br.com.fiap.orbitAlert.model.EstacaoIot;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-06-02T23:24:30-0300",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.10 (Oracle Corporation)"
)
@Component
public class IEstacaoIotMapperImpl implements IEstacaoIotMapper {

    @Override
    public EstacaoIotDTO toDTO(EstacaoIot entity) {
        if ( entity == null ) {
            return null;
        }

        EstacaoIotDTO estacaoIotDTO = new EstacaoIotDTO();

        estacaoIotDTO.setNmEstacao( entity.getNmEstacao() );
        estacaoIotDTO.setDsLocalizacao( entity.getDsLocalizacao() );
        estacaoIotDTO.setStAtivo( entity.getStAtivo() );
        estacaoIotDTO.setZonaRisco( entity.getZonaRisco() );

        return estacaoIotDTO;
    }

    @Override
    public EstacaoIot toEntity(EstacaoIotDTO dto) {
        if ( dto == null ) {
            return null;
        }

        EstacaoIot estacaoIot = new EstacaoIot();

        estacaoIot.setNmEstacao( dto.getNmEstacao() );
        estacaoIot.setDsLocalizacao( dto.getDsLocalizacao() );
        estacaoIot.setStAtivo( dto.getStAtivo() );
        estacaoIot.setZonaRisco( dto.getZonaRisco() );

        return estacaoIot;
    }
}
