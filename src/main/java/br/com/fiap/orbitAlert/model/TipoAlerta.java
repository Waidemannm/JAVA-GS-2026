package br.com.fiap.orbitAlert.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Schema(description = "Entidade que representa a tabela TB_TIPO_ALERTA no Oracle DB. Domínio: DESLIZAMENTO, ENCHENTE, SECA, EROSAO.")
@Entity
@Table(name = "TB_TIPO_ALERTA")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class TipoAlerta {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_tipo_alerta")
    @SequenceGenerator(name = "seq_tipo_alerta", sequenceName = "SEQ_TIPO_ALERTA", allocationSize = 1)
    @Column(name = "ID_TIPO_ALERTA")
    @Schema(description = "Identificador único do tipo de alerta.")
    private Long id;

    @NotBlank(message = "O nome do tipo é obrigatório")
    @Column(name = "NM_TIPO", nullable = false, unique = true, length = 50)
    @Schema(description = "Nome do tipo de alerta: DESLIZAMENTO, ENCHENTE, SECA ou EROSAO.")
    private String nmTipo;

    @Column(name = "DS_DESCRICAO", length = 300)
    @Schema(description = "Descrição da condição detectada que caracteriza este tipo de alerta.")
    private String dsDescricao;

    @JsonIgnore
    @OneToMany(mappedBy = "tipoAlerta", fetch = FetchType.LAZY)
    private List<Alerta> alertas;

    public void atualizar(TipoAlerta tipoAlerta) {
        this.nmTipo = tipoAlerta.getNmTipo();
        this.dsDescricao = tipoAlerta.getDsDescricao();
    }
}