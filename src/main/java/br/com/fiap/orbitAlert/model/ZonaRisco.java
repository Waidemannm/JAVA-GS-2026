package br.com.fiap.orbitAlert.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@Schema(description = "Entidade que representa a tabela TB_ZONA_RISCO no Oracle DB.")
@Entity
@Table(name = "TB_ZONA_RISCO")
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
public class ZonaRisco extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_zona_risco")
    @SequenceGenerator(name = "seq_zona_risco", sequenceName = "SEQ_ZONA_RISCO", allocationSize = 1)
    @Column(name = "ID_ZONA")
    @Schema(description = "Identificador único da zona de risco.")
    private Long id;

    @NotBlank(message = "O nome da zona é obrigatório")
    @Column(name = "NM_ZONA", nullable = false, length = 200)
    @Schema(description = "Nome descritivo da zona de risco.")
    private String nmZona;

    @Column(name = "DS_DESCRICAO", length = 500)
    @Schema(description = "Descrição detalhada das características da zona.")
    private String dsDescricao;

    @NotNull(message = "A latitude é obrigatória")
    @Column(name = "NR_LATITUDE", nullable = false)
    @Schema(description = "Latitude central da zona em graus decimais.")
    private Double nrLatitude;

    @NotNull(message = "A longitude é obrigatória")
    @Column(name = "NR_LONGITUDE", nullable = false)
    @Schema(description = "Longitude central da zona em graus decimais.")
    private Double nrLongitude;

    @Min(value = 1, message = "O limiar deve ser no mínimo 1")
    @Max(value = 5, message = "O limiar deve ser no máximo 5")
    @Column(name = "NR_LIMIAR_ALERTA", nullable = false)
    @Schema(description = "Índice mínimo para disparo automático de alerta. Valores de 1 a 5.")
    private Integer nrLimiarAlerta = 3;

    @Column(name = "ST_ATIVO", nullable = false, length = 1)
    @Schema(description = "Indica se a zona está ativa para monitoramento. S = ativo, N = inativo.")
    private String stAtivo = "S";

    @NotNull(message = "O município é obrigatório")
    @ManyToOne
    @JoinColumn(name = "ID_MUNICIPIO", nullable = false)
    @Schema(description = "Município ao qual a zona pertence.")
    private Municipio municipio;

    @JsonIgnore
    @OneToMany(mappedBy = "zonaRisco", fetch = FetchType.LAZY)
    private List<EstacaoIot> estacoes;

    @JsonIgnore
    @OneToMany(mappedBy = "zonaRisco", fetch = FetchType.LAZY)
    private List<Alerta> alertas;

    public void atualizar(ZonaRisco zonaRisco) {
        this.nmZona = zonaRisco.getNmZona();
        this.dsDescricao = zonaRisco.getDsDescricao();
        this.nrLatitude = zonaRisco.getNrLatitude();
        this.nrLongitude = zonaRisco.getNrLongitude();
        this.nrLimiarAlerta = zonaRisco.getNrLimiarAlerta();
        this.stAtivo = zonaRisco.getStAtivo();
        this.municipio = zonaRisco.getMunicipio();
    }
}