package br.com.fiap.orbitAlert.dto;

import br.com.fiap.orbitAlert.model.ZonaRisco;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "DTO da entidade EstacaoIot.")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EstacaoIotDTO {

    @NotBlank(message = "O nome da estação é obrigatório")
    @Schema(description = "Nome ou código identificador da estação ESP32.", example = "EST-001-PETROPOLIS-A")
    private String nmEstacao;

    @Schema(description = "Descrição textual da localização física da estação.", example = "Morro da Oficina - Encosta Norte")
    private String dsLocalizacao;

    @Schema(description = "Indica se a estação está operacional. S = ativo, N = inativo.", example = "S")
    private String stAtivo = "S";

    @NotNull(message = "A zona de risco é obrigatória")
    @Schema(description = "Zona de risco onde a estação está instalada.")
    private ZonaRisco zonaRisco;
}