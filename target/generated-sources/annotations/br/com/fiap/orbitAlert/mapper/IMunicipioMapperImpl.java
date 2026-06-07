package br.com.fiap.orbitAlert.mapper;

import br.com.fiap.orbitAlert.dto.MunicipioDTO;
import br.com.fiap.orbitAlert.model.Municipio;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-06-02T23:24:30-0300",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.10 (Oracle Corporation)"
)
@Component
public class IMunicipioMapperImpl implements IMunicipioMapper {

    @Override
    public MunicipioDTO toDTO(Municipio entity) {
        if ( entity == null ) {
            return null;
        }

        MunicipioDTO municipioDTO = new MunicipioDTO();

        municipioDTO.setNmMunicipio( entity.getNmMunicipio() );
        municipioDTO.setNmEstado( entity.getNmEstado() );
        municipioDTO.setNrLatitude( entity.getNrLatitude() );
        municipioDTO.setNrLongitude( entity.getNrLongitude() );
        municipioDTO.setNrPopulacao( entity.getNrPopulacao() );
        municipioDTO.setStAtivo( entity.getStAtivo() );

        return municipioDTO;
    }

    @Override
    public Municipio toEntity(MunicipioDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Municipio municipio = new Municipio();

        municipio.setNmMunicipio( dto.getNmMunicipio() );
        municipio.setNmEstado( dto.getNmEstado() );
        municipio.setNrLatitude( dto.getNrLatitude() );
        municipio.setNrLongitude( dto.getNrLongitude() );
        municipio.setNrPopulacao( dto.getNrPopulacao() );
        municipio.setStAtivo( dto.getStAtivo() );

        return municipio;
    }
}
