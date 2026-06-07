package br.com.fiap.orbitAlert.mapper;

import br.com.fiap.orbitAlert.dto.ZonaRiscoDTO;
import br.com.fiap.orbitAlert.model.ZonaRisco;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-06-02T23:24:30-0300",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.10 (Oracle Corporation)"
)
@Component
public class IZonaRiscoMapperImpl implements IZonaRiscoMapper {

    @Override
    public ZonaRiscoDTO toDTO(ZonaRisco entity) {
        if ( entity == null ) {
            return null;
        }

        ZonaRiscoDTO zonaRiscoDTO = new ZonaRiscoDTO();

        zonaRiscoDTO.setNmZona( entity.getNmZona() );
        zonaRiscoDTO.setDsDescricao( entity.getDsDescricao() );
        zonaRiscoDTO.setNrLatitude( entity.getNrLatitude() );
        zonaRiscoDTO.setNrLongitude( entity.getNrLongitude() );
        zonaRiscoDTO.setNrLimiarAlerta( entity.getNrLimiarAlerta() );
        zonaRiscoDTO.setStAtivo( entity.getStAtivo() );
        zonaRiscoDTO.setMunicipio( entity.getMunicipio() );

        return zonaRiscoDTO;
    }

    @Override
    public ZonaRisco toEntity(ZonaRiscoDTO dto) {
        if ( dto == null ) {
            return null;
        }

        ZonaRisco zonaRisco = new ZonaRisco();

        zonaRisco.setNmZona( dto.getNmZona() );
        zonaRisco.setDsDescricao( dto.getDsDescricao() );
        zonaRisco.setNrLatitude( dto.getNrLatitude() );
        zonaRisco.setNrLongitude( dto.getNrLongitude() );
        zonaRisco.setNrLimiarAlerta( dto.getNrLimiarAlerta() );
        zonaRisco.setStAtivo( dto.getStAtivo() );
        zonaRisco.setMunicipio( dto.getMunicipio() );

        return zonaRisco;
    }
}
