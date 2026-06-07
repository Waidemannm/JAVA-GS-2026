package br.com.fiap.orbitAlert.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "DTO da entidade TipoAlerta.")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TipoAlertaDTO {

    @NotBlank(message = "O nome do tipo é obrigatório")
    @Schema(description = "Nome do tipo de alerta: DESLIZAMENTO, ENCHENTE, SECA ou EROSAO.", example = "DESLIZAMENTO")
    private String nmTipo;

    @Schema(description = "Descrição da condição detectada que caracteriza este tipo de alerta.", example = "Risco de deslizamento de terra detectado por variação de solo via satélite")
    private String dsDescricao;
}