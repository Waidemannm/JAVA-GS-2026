package br.com.fiap.orbitAlert.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Schema(description = "Entidade que representa a tabela TB_ESTACAO_IOT no Oracle DB.")
@Entity
@Table(name = "TB_ESTACAO_IOT")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class EstacaoIot {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_estacao_iot")
    @SequenceGenerator(name = "seq_estacao_iot", sequenceName = "SEQ_ESTACAO_IOT", allocationSize = 1)
    @Column(name = "ID_ESTACAO")
    @Schema(description = "Identificador único da estação IoT.")
    private Long id;

    @NotBlank(message = "O nome da estação é obrigatório")
    @Column(name = "NM_ESTACAO", nullable = false, length = 200)
    @Schema(description = "Nome ou código identificador da estação ESP32.")
    private String nmEstacao;

    @Column(name = "DS_LOCALIZACAO", length = 300)
    @Schema(description = "Descrição textual da localização física da estação.")
    private String dsLocalizacao;

    @Column(name = "ST_ATIVO", nullable = false, length = 1)
    @Schema(description = "Indica se a estação está operacional. S = ativo, N = inativo.")
    private String stAtivo = "S";

    @Column(name = "DT_INSTALACAO", nullable = false, updatable = false, insertable = false)
    @Schema(description = "Data de instalação da estação.")
    private LocalDate dtInstalacao;

    @NotNull(message = "A zona de risco é obrigatória")
    @ManyToOne
    @JoinColumn(name = "ID_ZONA", nullable = false)
    @Schema(description = "Zona de risco onde a estação está instalada.")
    private ZonaRisco zonaRisco;

    @JsonIgnore
    @OneToMany(mappedBy = "estacaoIot", fetch = FetchType.LAZY)
    private List<LeituraIot> leituras;

    public void atualizar(EstacaoIot estacaoIot) {
        this.nmEstacao = estacaoIot.getNmEstacao();
        this.dsLocalizacao = estacaoIot.getDsLocalizacao();
        this.stAtivo = estacaoIot.getStAtivo();
        this.zonaRisco = estacaoIot.getZonaRisco();
    }
}