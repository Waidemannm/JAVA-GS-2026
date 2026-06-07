package br.com.fiap.orbitAlert.records;

import br.com.fiap.orbitAlert.model.enums.StatusAlertaEnum;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(description = "Record imutável com resumo de um alerta. Usado para listagens rápidas.")
public record AlertaResumoRecord(

        @Schema(description = "ID do alerta.", example = "1")
        Long id,

        @Schema(description = "Nome da zona de risco.", example = "Morro da Oficina")
        String nmZona,

        @Schema(description = "Nome do município.", example = "Petropolis")
        String nmMunicipio,

        @Schema(description = "Tipo do alerta.", example = "DESLIZAMENTO")
        String nmTipo,

        @Schema(description = "Nível de risco de 1 a 5.", example = "4")
        Integer nrNivelRisco,

        @Schema(description = "Status atual do alerta.", example = "ATIVO")
        StatusAlertaEnum stStatus,

        @Schema(description = "Data de criação do alerta.")
        LocalDateTime dtCriacao
) {}
