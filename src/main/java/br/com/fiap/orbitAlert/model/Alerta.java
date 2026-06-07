package br.com.fiap.orbitAlert.model;

import br.com.fiap.orbitAlert.model.enums.StatusAlertaEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Schema(description = "Entidade que representa a tabela TB_ALERTA no Oracle DB. Alertas gerados automaticamente pela API Java.")
@Entity
@Table(name = "TB_ALERTA")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Alerta {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_alerta")
    @SequenceGenerator(name = "seq_alerta", sequenceName = "SEQ_ALERTA", allocationSize = 1)
    @Column(name = "ID_ALERTA")
    @Schema(description = "Identificador único do alerta.")
    private Long id;

    @NotNull(message = "O nível de risco é obrigatório")
    @Min(value = 1, message = "O nível de risco deve ser no mínimo 1")
    @Max(value = 5, message = "O nível de risco deve ser no máximo 5")
    @Column(name = "NR_NIVEL_RISCO", nullable = false)
    @Schema(description = "Nível de risco no momento da criação. Valores de 1 a 5.")
    private Integer nrNivelRisco;

    @NotNull(message = "O status é obrigatório")
    @Enumerated(EnumType.STRING)
    @Column(name = "ST_STATUS", nullable = false, length = 20)
    @Schema(description = "Status atual do alerta: ATIVO, EM_ATENDIMENTO, FECHADO ou CANCELADO.")
    private StatusAlertaEnum stStatus;

    @Column(name = "DS_OBSERVACAO", length = 1000)
    @Schema(description = "Observações adicionais sobre o alerta.")
    private String dsObservacao;

    @Column(name = "DT_CRIACAO", nullable = false, updatable = false, insertable = false)
    @Schema(description = "Data e hora de criação automática do alerta.")
    private LocalDateTime dtCriacao;

    @Column(name = "DT_FECHAMENTO")
    @Schema(description = "Data e hora do fechamento. Nulo enquanto o alerta estiver ativo.")
    private LocalDateTime dtFechamento;

    @NotNull(message = "A zona de risco é obrigatória")
    @ManyToOne
    @JoinColumn(name = "ID_ZONA", nullable = false)
    @Schema(description = "Zona de risco que originou o alerta.")
    private ZonaRisco zonaRisco;

    @NotNull(message = "O tipo de alerta é obrigatório")
    @ManyToOne
    @JoinColumn(name = "ID_TIPO_ALERTA", nullable = false)
    @Schema(description = "Tipo do alerta: deslizamento, enchente, seca ou erosão.")
    private TipoAlerta tipoAlerta;

    @JsonIgnore
    @OneToOne(mappedBy = "alerta", fetch = FetchType.LAZY)
    private AnaliseIa analiseIa;

    @JsonIgnore
    @OneToMany(mappedBy = "alerta", fetch = FetchType.LAZY)
    private List<HistoricoAlerta> historicos;

    @JsonIgnore
    @OneToMany(mappedBy = "alerta", fetch = FetchType.LAZY)
    private List<Notificacao> notificacoes;

    public void atualizar(Alerta alerta) {
        this.nrNivelRisco = alerta.getNrNivelRisco();
        this.stStatus = alerta.getStStatus();
        this.dsObservacao = alerta.getDsObservacao();
        this.dtFechamento = alerta.getDtFechamento();
        this.zonaRisco = alerta.getZonaRisco();
        this.tipoAlerta = alerta.getTipoAlerta();
    }
}