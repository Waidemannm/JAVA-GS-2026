package br.com.fiap.orbitAlert.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Schema(description = "Entidade que representa a tabela TB_HISTORICO_ALERTA no Oracle DB. Populada automaticamente pela trigger TRG_HISTORICO_ALERTA.")
@Entity
@Table(name = "TB_HISTORICO_ALERTA")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class HistoricoAlerta {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_historico_alerta")
    @SequenceGenerator(name = "seq_historico_alerta", sequenceName = "SEQ_HISTORICO_ALERTA", allocationSize = 1)
    @Column(name = "ID_HISTORICO")
    @Schema(description = "Identificador único do registro de histórico.")
    private Long id;

    @Column(name = "ST_STATUS_ANT", length = 20)
    @Schema(description = "Status anterior do alerta antes da mudança.")
    private String stStatusAnt;

    @NotBlank(message = "O novo status é obrigatório")
    @Column(name = "ST_STATUS_NOVO", nullable = false, length = 20)
    @Schema(description = "Novo status do alerta após a mudança.")
    private String stStatusNovo;

    @Column(name = "NR_INDICE_RISCO")
    @Schema(description = "Índice de risco vigente no momento da mudança.")
    private Integer nrIndiceRisco;

    @Column(name = "DS_OBSERVACAO", length = 500)
    @Schema(description = "Observação registrada no momento da mudança.")
    private String dsObservacao;

    @Column(name = "NM_USUARIO_MOD", length = 150)
    @Schema(description = "Nome do usuário que realizou a modificação.")
    private String nmUsuarioMod;

    @Column(name = "DT_ALTERACAO", nullable = false, updatable = false, insertable = false)
    @Schema(description = "Data e hora da mudança de status.")
    private LocalDateTime dtAlteracao;

    @NotNull(message = "O alerta é obrigatório")
    @ManyToOne
    @JoinColumn(name = "ID_ALERTA", nullable = false)
    @Schema(description = "Alerta ao qual este histórico pertence.")
    private Alerta alerta;

    public void atualizar(HistoricoAlerta historicoAlerta) {
        this.stStatusAnt = historicoAlerta.getStStatusAnt();
        this.stStatusNovo = historicoAlerta.getStStatusNovo();
        this.nrIndiceRisco = historicoAlerta.getNrIndiceRisco();
        this.dsObservacao = historicoAlerta.getDsObservacao();
        this.nmUsuarioMod = historicoAlerta.getNmUsuarioMod();
        this.dtAlteracao = historicoAlerta.getDtAlteracao();
    }
}