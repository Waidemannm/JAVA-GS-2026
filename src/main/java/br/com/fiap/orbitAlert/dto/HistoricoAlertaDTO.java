package br.com.fiap.orbitAlert.dto;

import br.com.fiap.orbitAlert.model.Alerta;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "DTO da entidade HistoricoAlerta.")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HistoricoAlertaDTO {

    @Schema(description = "Status anterior do alerta antes da mudança.", example = "ATIVO")
    private String stStatusAnt;

    @NotBlank(message = "O novo status é obrigatório")
    @Schema(description = "Novo status do alerta após a mudança.", example = "EM_ATENDIMENTO")
    private String stStatusNovo;

    @Schema(description = "Índice de risco vigente no momento da mudança.", example = "4")
    private Integer nrIndiceRisco;

    @Schema(description = "Observação registrada no momento da mudança.", example = "Gestor Carlos iniciou protocolo de resposta")
    private String dsObservacao;

    @Schema(description = "Nome do usuário que realizou a modificação.", example = "Carlos Defesa Civil RJ")
    private String nmUsuarioMod;

    @NotNull(message = "O alerta é obrigatório")
    @Schema(description = "Alerta ao qual este histórico pertence.")
    private Alerta alerta;
}