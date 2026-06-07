package br.com.fiap.orbitAlert.dto;

import br.com.fiap.orbitAlert.model.Alerta;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "DTO da entidade AnaliseIa. Representa a análise gerada pela Claude API para um alerta.")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnaliseIaDTO {

    @Schema(description = "Prompt enviado à Claude API com os dados do alerta.", example = "Analise de risco: Morro da Oficina, nivel 4, umidade 88%, chuva 25mm")
    private String dsPrompt;

    @NotBlank(message = "A resposta da IA é obrigatória")
    @Schema(description = "Análise contextual completa retornada pela Claude API.", example = "Zona com risco ALTO. Recomendação: evacuação preventiva de encostas acima de 30 graus.")
    private String dsResposta;

    @Schema(description = "Identificador do modelo de IA utilizado.", example = "claude-sonnet-4-20250514")
    private String dsModeloIa = "claude-sonnet-4-20250514";

    @Schema(description = "Quantidade de tokens consumidos na geração da análise.", example = "842")
    private Long nrTokenUsados;

    @NotNull(message = "O alerta é obrigatório")
    @Schema(description = "Alerta ao qual esta análise está vinculada.")
    private Alerta alerta;
}