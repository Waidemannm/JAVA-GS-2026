package br.com.fiap.orbitAlert.dto;

import br.com.fiap.orbitAlert.model.Alerta;
import br.com.fiap.orbitAlert.model.Usuario;
import br.com.fiap.orbitAlert.model.enums.TipoNotificacaoEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "DTO da entidade Notificacao.")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificacaoDTO {

    @NotNull(message = "O tipo de notificação é obrigatório")
    @Schema(description = "Tipo de notificação: ALERTA, URGENTE ou INFO.", example = "URGENTE")
    private TipoNotificacaoEnum tpNotificacao;

    @NotBlank(message = "O título é obrigatório")
    @Schema(description = "Título exibido no app mobile do gestor.", example = "ALERTA NÍVEL 4 - Morro da Oficina")
    private String dsTitulo;

    @Schema(description = "Corpo da notificação com detalhes do alerta.", example = "Risco ALTO detectado. Índice 4/5. Acesse o app para detalhes.")
    private String dsMensagem;

    @Schema(description = "Indica se o gestor visualizou a notificação. S = lida, N = não lida.", example = "N")
    private String stLida = "N";

    @NotNull(message = "O usuário é obrigatório")
    @Schema(description = "Gestor destinatário da notificação.")
    private Usuario usuario;

    @NotNull(message = "O alerta é obrigatório")
    @Schema(description = "Alerta que originou a notificação.")
    private Alerta alerta;
}