package br.com.fiap.orbitAlert.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@Schema(description = "Entidade que representa a tabela TB_MUNICIPIO no Oracle DB.")
@Entity
@Table(name = "TB_MUNICIPIO")
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
public class Municipio extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_municipio")
    @SequenceGenerator(name = "seq_municipio", sequenceName = "SEQ_MUNICIPIO", allocationSize = 1)
    @Column(name = "ID_MUNICIPIO")
    @Schema(description = "Identificador único do município.")
    private Long id;

    @NotBlank(message = "O nome do município é obrigatório")
    @Column(name = "NM_MUNICIPIO", nullable = false, length = 200)
    @Schema(description = "Nome oficial do município.")
    private String nmMunicipio;

    @NotBlank(message = "O estado é obrigatório")
    @Column(name = "NM_ESTADO", nullable = false, length = 100)
    @Schema(description = "Nome do estado por extenso.")
    private String nmEstado;

    @NotNull(message = "A latitude é obrigatória")
    @Column(name = "NR_LATITUDE", nullable = false)
    @Schema(description = "Latitude do centroide do município em graus decimais.")
    private Double nrLatitude;

    @NotNull(message = "A longitude é obrigatória")
    @Column(name = "NR_LONGITUDE", nullable = false)
    @Schema(description = "Longitude do centroide do município em graus decimais.")
    private Double nrLongitude;

    @Column(name = "NR_POPULACAO")
    @Schema(description = "População estimada conforme IBGE.")
    private Long nrPopulacao;

    @Column(name = "ST_ATIVO", nullable = false, length = 1)
    @Schema(description = "Indica se o município está ativo para monitoramento. S = ativo, N = inativo.")
    private String stAtivo = "S";

    @JsonIgnore
    @OneToMany(mappedBy = "municipio", fetch = FetchType.LAZY)
    private List<UsuarioMunicipio> usuarios;

    @JsonIgnore
    @OneToMany(mappedBy = "municipio", fetch = FetchType.LAZY)
    private List<ZonaRisco> zonas;

    public void atualizar(Municipio municipio) {
        this.nmMunicipio = municipio.getNmMunicipio();
        this.nmEstado = municipio.getNmEstado();
        this.nrLatitude = municipio.getNrLatitude();
        this.nrLongitude = municipio.getNrLongitude();
        this.nrPopulacao = municipio.getNrPopulacao();
        this.stAtivo = municipio.getStAtivo();
    }
}