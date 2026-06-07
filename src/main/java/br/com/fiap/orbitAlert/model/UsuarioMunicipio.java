package br.com.fiap.orbitAlert.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Schema(description = "Entidade que representa a tabela TB_USUARIO_MUNICIPIO no Oracle DB. Relacionamento N:N entre usuário e município.")
@Entity
@Table(name = "TB_USUARIO_MUNICIPIO")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UsuarioMunicipio {

    @EmbeddedId
    @Schema(description = "Chave primária composta: ID_USUARIO + ID_MUNICIPIO.")
    private UsuarioMunicipioPK id;

    @ManyToOne
    @MapsId("idUsuario")
    @JoinColumn(name = "ID_USUARIO", nullable = false)
    @Schema(description = "Usuário vinculado ao município.")
    private Usuario usuario;

    @ManyToOne
    @MapsId("idMunicipio")
    @JoinColumn(name = "ID_MUNICIPIO", nullable = false)
    @Schema(description = "Município vinculado ao usuário.")
    private Municipio municipio;

    @Column(name = "DT_VINCULO", nullable = false, updatable = false, insertable = false)
    @Schema(description = "Data em que o vínculo foi criado.")
    private LocalDate dtVinculo;
}