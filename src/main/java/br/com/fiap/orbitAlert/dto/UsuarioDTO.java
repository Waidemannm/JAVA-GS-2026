package br.com.fiap.orbitAlert.dto;

import br.com.fiap.orbitAlert.model.enums.TipoPerfilEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "DTO da entidade Usuario.")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDTO {

    @NotBlank(message = "O nome é obrigatório")
    @Schema(description = "Nome completo do usuário.", example = "Carlos Defesa Civil RJ")
    private String nmUsuario;

    @NotBlank(message = "O e-mail é obrigatório")
    @Email(message = "E-mail inválido")
    @Schema(description = "E-mail de acesso. Deve ser único no sistema.", example = "carlos.defesa@rj.gov.br")
    private String dsEmail;

    @NotBlank(message = "A senha é obrigatória")
    @Schema(description = "Senha do usuário. Será armazenada como hash BCrypt.", example = "senha123")
    private String dsSenhaHash;

    @NotNull(message = "O perfil é obrigatório")
    @Schema(description = "Perfil de acesso: ADMIN ou GESTOR.", example = "GESTOR")
    private TipoPerfilEnum tpPerfil;

    @Schema(description = "Indica se o usuário está ativo. S = ativo, N = inativo.", example = "S")
    private String stAtivo = "S";
}