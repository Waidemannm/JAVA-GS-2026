package br.com.fiap.orbitAlert.dto;

import br.com.fiap.orbitAlert.model.Municipio;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "DTO da entidade ZonaRisco.")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ZonaRiscoDTO {

    @NotBlank(message = "O nome da zona é obrigatório")
    @Schema(description = "Nome descritivo da zona de risco.", example = "Morro da Oficina")
    private String nmZona;

    @Schema(description = "Descrição detalhada das características da zona.", example = "Encosta com alto risco de deslizamento em período chuvoso")
    private String dsDescricao;

    @NotNull(message = "A latitude é obrigatória")
    @Schema(description = "Latitude central da zona em graus decimais.", example = "-22.5100")
    private Double nrLatitude;

    @NotNull(message = "A longitude é obrigatória")
    @Schema(description = "Longitude central da zona em graus decimais.", example = "-43.1750")
    private Double nrLongitude;

    @Min(value = 1, message = "O limiar deve ser no mínimo 1")
    @Max(value = 5, message = "O limiar deve ser no máximo 5")
    @Schema(description = "Índice mínimo para disparo automático de alerta. Valores de 1 a 5.", example = "3")
    private Integer nrLimiarAlerta = 3;

    @Schema(description = "Indica se a zona está ativa para monitoramento. S = ativo, N = inativo.", example = "S")
    private String stAtivo = "S";

    @NotNull(message = "O município é obrigatório")
    @Schema(description = "Município ao qual a zona pertence.")
    private Municipio municipio;
}