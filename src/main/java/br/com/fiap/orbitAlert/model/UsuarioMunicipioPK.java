package br.com.fiap.orbitAlert.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Schema(description = "Chave primária composta da entidade UsuarioMunicipio.")
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UsuarioMunicipioPK implements Serializable {

    @Column(name = "ID_USUARIO")
    @Schema(description = "ID do usuário na chave composta.")
    private Long idUsuario;

    @Column(name = "ID_MUNICIPIO")
    @Schema(description = "ID do município na chave composta.")
    private Long idMunicipio;
}