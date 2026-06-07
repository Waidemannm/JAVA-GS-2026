package br.com.fiap.orbitAlert.records;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Record imutável representando o payload publicado pelo ESP32 via MQTT.")
public record LeituraIotRecord(

        @Schema(description = "ID da estação IoT que publicou a leitura.", example = "1")
        Long idEstacao,

        @Schema(description = "Temperatura do ar em graus Celsius.", example = "21.8")
        Double nrTemperatura,

        @Schema(description = "Umidade relativa do ar em %.", example = "88.5")
        Double nrUmidade,

        @Schema(description = "Precipitação acumulada em milímetros.", example = "25.5")
        Double nrChuvaMm,

        @Schema(description = "Índice de risco calculado pela API. Valores de 1 a 5.", example = "4")
        Integer nrIndiceRisco
) {}