package br.com.fiap.orbitAlert.mapper;

import br.com.fiap.orbitAlert.dto.AnaliseIaDTO;
import br.com.fiap.orbitAlert.model.AnaliseIa;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-06-02T23:24:30-0300",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.10 (Oracle Corporation)"
)
@Component
public class IAnaliseIaMapperImpl implements IAnaliseIaMapper {

    @Override
    public AnaliseIaDTO toDTO(AnaliseIa entity) {
        if ( entity == null ) {
            return null;
        }

        AnaliseIaDTO analiseIaDTO = new AnaliseIaDTO();

        analiseIaDTO.setDsPrompt( entity.getDsPrompt() );
        analiseIaDTO.setDsResposta( entity.getDsResposta() );
        analiseIaDTO.setDsModeloIa( entity.getDsModeloIa() );
        analiseIaDTO.setNrTokenUsados( entity.getNrTokenUsados() );
        analiseIaDTO.setAlerta( entity.getAlerta() );

        return analiseIaDTO;
    }

    @Override
    public AnaliseIa toEntity(AnaliseIaDTO dto) {
        if ( dto == null ) {
            return null;
        }

        AnaliseIa analiseIa = new AnaliseIa();

        analiseIa.setDsPrompt( dto.getDsPrompt() );
        analiseIa.setDsResposta( dto.getDsResposta() );
        analiseIa.setDsModeloIa( dto.getDsModeloIa() );
        analiseIa.setNrTokenUsados( dto.getNrTokenUsados() );
        analiseIa.setAlerta( dto.getAlerta() );

        return analiseIa;
    }
}
