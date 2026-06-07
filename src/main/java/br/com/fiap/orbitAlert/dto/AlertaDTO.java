package br.com.fiap.orbitAlert.dto;

import br.com.fiap.orbitAlert.model.TipoAlerta;
import br.com.fiap.orbitAlert.model.ZonaRisco;
import br.com.fiap.orbitAlert.model.enums.StatusAlertaEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Schema(description = "DTO da entidade Alerta.")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlertaDTO {

    @NotNull(message = "O nível de risco é obrigatório")
    @Min(value = 1, message = "O nível de risco deve ser no mínimo 1")
    @Max(value = 5, message = "O nível de risco deve ser no máximo 5")
    @Schema(description = "Nível de risco do alerta.", example = "4")
    private Integer nrNivelRisco;

    @NotNull(message = "O status é obrigatório")
    @Schema(description = "Status do alerta.", example = "ATIVO")
    private StatusAlertaEnum stStatus;

    @Schema(description = "Observações adicionais sobre o alerta.", example = "Deslizamento confirmado após chuva intensa de 48h")
    private String dsObservacao;

    @Schema(description = "Data e hora do fechamento do alerta.", example = "2026-05-10T12:00:00")
    private LocalDateTime dtFechamento;

    @NotNull(message = "A zona de risco é obrigatória")
    @Schema(description = "Zona de risco que originou o alerta.")
    private ZonaRisco zonaRisco;

    @NotNull(message = "O tipo de alerta é obrigatório")
    @Schema(description = "Tipo do alerta: DESLIZAMENTO, ENCHENTE, SECA ou EROSAO.")
    private TipoAlerta tipoAlerta;
}