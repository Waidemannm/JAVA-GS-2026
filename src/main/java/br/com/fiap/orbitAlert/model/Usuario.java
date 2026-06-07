package br.com.fiap.orbitAlert.model;

import br.com.fiap.orbitAlert.model.enums.TipoPerfilEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@Schema(description = "Entidade que representa a tabela TB_USUARIO no Oracle DB.")
@Entity
@Table(name = "TB_USUARIO")
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
public class Usuario extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_usuario")
    @SequenceGenerator(name = "seq_usuario", sequenceName = "SEQ_USUARIO", allocationSize = 1)
    @Column(name = "ID_USUARIO")
    @Schema(description = "Identificador único do usuário.")
    private Long id;

    @NotBlank(message = "O nome é obrigatório")
    @Column(name = "NM_USUARIO", nullable = false, length = 150)
    @Schema(description = "Nome completo do usuário.")
    private String nmUsuario;

    @NotBlank(message = "O e-mail é obrigatório")
    @Email(message = "E-mail inválido")
    @Column(name = "DS_EMAIL", nullable = false, unique = true, length = 200)
    @Schema(description = "E-mail de acesso. Deve ser único no sistema.")
    private String dsEmail;

    @NotBlank(message = "A senha é obrigatória")
    @Column(name = "DS_SENHA_HASH", nullable = false, length = 500)
    @Schema(description = "Hash BCrypt da senha gerado pela API Java.")
    private String dsSenhaHash;

    @NotNull(message = "O perfil é obrigatório")
    @Enumerated(EnumType.STRING)
    @Column(name = "TP_PERFIL", nullable = false, length = 10)
    @Schema(description = "Perfil de acesso: ADMIN ou GESTOR.")
    private TipoPerfilEnum tpPerfil;

    @Column(name = "ST_ATIVO", nullable = false, length = 1)
    @Schema(description = "Indica se o usuário está ativo. S = ativo, N = inativo.")
    private String stAtivo = "S";

    @JsonIgnore
    @OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY)
    private List<UsuarioMunicipio> municipios;

    @JsonIgnore
    @OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY)
    private List<Notificacao> notificacoes;

    public void atualizar(Usuario usuario) {
        this.nmUsuario = usuario.getNmUsuario();
        this.dsEmail = usuario.getDsEmail();
        this.dsSenhaHash = usuario.getDsSenhaHash();
        this.tpPerfil = usuario.getTpPerfil();
        this.stAtivo = usuario.getStAtivo();
    }
}