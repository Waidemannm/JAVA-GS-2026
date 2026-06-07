package br.com.fiap.orbitAlert.dto;

import br.com.fiap.orbitAlert.model.Municipio;
import br.com.fiap.orbitAlert.model.Usuario;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "DTO da entidade UsuarioMunicipio. Representa o vínculo N:N entre usuário e município.")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioMunicipioDTO {

    @NotNull(message = "O usuário é obrigatório")
    @Schema(description = "Usuário a ser vinculado ao município.")
    private Usuario usuario;

    @NotNull(message = "O município é obrigatório")
    @Schema(description = "Município a ser vinculado ao usuário.")
    private Municipio municipio;
}