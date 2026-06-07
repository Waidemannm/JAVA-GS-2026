package br.com.fiap.orbitAlert.records;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Record imutável com resumo de risco de um município. Usado no dashboard.")
public record MunicipioRiscoRecord(

        @Schema(description = "ID do município.", example = "1")
        Long idMunicipio,

        @Schema(description = "Nome do município.", example = "Petropolis")
        String nmMunicipio,

        @Schema(description = "Nome do estado.", example = "Rio de Janeiro")
        String nmEstado,

        @Schema(description = "Quantidade de alertas ativos.", example = "3")
        Long qtAlertasAtivos,

        @Schema(description = "Índice médio de risco das zonas monitoradas.", example = "4.2")
        Double nrIndicesMedioRisco,

        @Schema(description = "Quantidade de estações IoT ativas.", example = "2")
        Long qtEstacoesAtivas
) {}