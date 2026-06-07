package br.com.fiap.orbitAlert.dto;

import br.com.fiap.orbitAlert.model.EstacaoIot;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "DTO da entidade LeituraIot. Representa uma leitura publicada pelo ESP32 via MQTT.")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LeituraIotDTO {

    @NotNull(message = "A temperatura é obrigatória")
    @Schema(description = "Temperatura do ar em graus Celsius lida pelo DHT22.", example = "21.8")
    private Double nrTemperatura;

    @NotNull(message = "A umidade é obrigatória")
    @DecimalMin(value = "0.0", message = "Umidade deve ser no mínimo 0%")
    @DecimalMax(value = "100.0", message = "Umidade deve ser no máximo 100%")
    @Schema(description = "Umidade relativa do ar em % lida pelo DHT22.", example = "88.5")
    private Double nrUmidade;

    @Schema(description = "Precipitação acumulada em milímetros.", example = "25.5")
    private Double nrChuvaMm = 0.0;

    @NotNull(message = "O índice de risco é obrigatório")
    @Min(value = 1, message = "O índice de risco deve ser no mínimo 1")
    @Max(value = 5, message = "O índice de risco deve ser no máximo 5")
    @Schema(description = "Índice de risco calculado pela API Java. Valores de 1 a 5.", example = "4")
    private Integer nrIndiceRisco;

    @NotNull(message = "A estação é obrigatória")
    @Schema(description = "Estação ESP32 que originou a leitura.")
    private EstacaoIot estacaoIot;
}