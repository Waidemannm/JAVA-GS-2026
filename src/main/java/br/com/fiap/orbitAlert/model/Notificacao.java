package br.com.fiap.orbitAlert.model;

import br.com.fiap.orbitAlert.model.enums.TipoNotificacaoEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Schema(description = "Entidade que representa a tabela TB_NOTIFICACAO no Oracle DB.")
@Entity
@Table(name = "TB_NOTIFICACAO")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Notificacao {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_notificacao")
    @SequenceGenerator(name = "seq_notificacao", sequenceName = "SEQ_NOTIFICACAO", allocationSize = 1)
    @Column(name = "ID_NOTIFICACAO")
    @Schema(description = "Identificador único da notificação.")
    private Long id;

    @NotNull(message = "O tipo de notificação é obrigatório")
    @Enumerated(EnumType.STRING)
    @Column(name = "TP_NOTIFICACAO", nullable = false, length = 20)
    @Schema(description = "Tipo de notificação: ALERTA, URGENTE ou INFO.")
    private TipoNotificacaoEnum tpNotificacao;

    @NotBlank(message = "O título é obrigatório")
    @Column(name = "DS_TITULO", nullable = false, length = 200)
    @Schema(description = "Título exibido no app mobile do gestor.")
    private String dsTitulo;

    @Column(name = "DS_MENSAGEM", length = 2000)
    @Schema(description = "Corpo da notificação com detalhes do alerta.")
    private String dsMensagem;

    @Column(name = "ST_LIDA", nullable = false, length = 1)
    @Schema(description = "Indica se o gestor visualizou a notificação. S = lida, N = não lida.")
    private String stLida = "N";

    @Column(name = "DT_ENVIO", nullable = false, updatable = false, insertable = false)
    @Schema(description = "Data e hora do envio da notificação.")
    private LocalDateTime dtEnvio;

    @NotNull(message = "O usuário é obrigatório")
    @ManyToOne
    @JoinColumn(name = "ID_USUARIO", nullable = false)
    @Schema(description = "Gestor destinatário da notificação.")
    private Usuario usuario;

    @NotNull(message = "O alerta é obrigatório")
    @ManyToOne
    @JoinColumn(name = "ID_ALERTA", nullable = false)
    @Schema(description = "Alerta que originou a notificação.")
    private Alerta alerta;

    public void atualizar(Notificacao notificacao) {
        this.tpNotificacao = notificacao.getTpNotificacao();
        this.dsTitulo = notificacao.getDsTitulo();
        this.dsMensagem = notificacao.getDsMensagem();
        this.stLida = notificacao.getStLida();
        this.usuario = notificacao.getUsuario();
        this.alerta = notificacao.getAlerta();
    }
}