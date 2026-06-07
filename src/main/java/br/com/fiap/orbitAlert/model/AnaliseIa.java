package br.com.fiap.orbitAlert.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Schema(description = "Entidade que representa a tabela TB_ANALISE_IA no Oracle DB. Análise gerada pela Claude API — relação 1:1 com Alerta.")
@Entity
@Table(name = "TB_ANALISE_IA")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AnaliseIa {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_analise_ia")
    @SequenceGenerator(name = "seq_analise_ia", sequenceName = "SEQ_ANALISE_IA", allocationSize = 1)
    @Column(name = "ID_ANALISE")
    @Schema(description = "Identificador único da análise de IA.")
    private Long id;

    @Column(name = "DS_PROMPT", length = 2000)
    @Schema(description = "Prompt enviado à Claude API com os dados do alerta.")
    private String dsPrompt;

    @NotBlank(message = "A resposta da IA é obrigatória")
    @Lob
    @Column(name = "DS_RESPOSTA", nullable = false)
    @Schema(description = "Análise contextual completa retornada pela Claude API.")
    private String dsResposta;

    @Column(name = "DS_MODELO_IA", length = 100)
    @Schema(description = "Identificador do modelo de IA utilizado.")
    private String dsModeloIa = "claude-sonnet-4-20250514";

    @Column(name = "NR_TOKENS_USADOS")
    @Schema(description = "Quantidade de tokens consumidos na geração da análise.")
    private Long nrTokenUsados;

    @Column(name = "DT_GERACAO", nullable = false, updatable = false, insertable = false)
    @Schema(description = "Data e hora da geração da análise.")
    private LocalDateTime dtGeracao;

    @NotNull(message = "O alerta é obrigatório")
    @OneToOne
    @JoinColumn(name = "ID_ALERTA", nullable = false, unique = true)
    @Schema(description = "Alerta ao qual esta análise está vinculada. Relação 1:1.")
    private Alerta alerta;

    public void atualizar(AnaliseIa analiseIa) {
        this.dsPrompt = analiseIa.getDsPrompt();
        this.dsResposta = analiseIa.getDsResposta();
        this.dsModeloIa = analiseIa.getDsModeloIa();
        this.nrTokenUsados = analiseIa.getNrTokenUsados();
        this.alerta = analiseIa.getAlerta();
    }
}