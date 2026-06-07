package br.com.fiap.orbitAlert.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Schema(description = "Entidade que representa a tabela TB_LOG_ERRO no Oracle DB.")
@Entity
@Table(name = "TB_LOG_ERRO")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class TbLogErro {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_log_erro")
    @SequenceGenerator(name = "seq_log_erro", sequenceName = "SEQ_LOG_ERRO", allocationSize = 1)
    @Column(name = "ID_LOG")
    @Schema(description = "Identificador único do log de erro.")
    private Long id;

    @Column(name = "NM_PROCEDURE", nullable = false, length = 100)
    @Schema(description = "Nome da procedure que gerou o erro.")
    private String nmProcedure;

    @Column(name = "NM_USUARIO", nullable = false, length = 100)
    @Schema(description = "Usuário Oracle conectado no momento do erro.")
    private String nmUsuario;

    @Column(name = "DT_OCORRENCIA", nullable = false, updatable = false, insertable = false)
    @Schema(description = "Data e hora da ocorrência do erro.")
    private LocalDateTime dtOcorrencia;

    @Column(name = "CD_ERRO", nullable = false)
    @Schema(description = "Código numérico do erro Oracle (SQLCODE).")
    private Long cdErro;

    @Column(name = "DS_MENSAGEM", length = 4000)
    @Schema(description = "Mensagem descritiva do erro (SQLERRM).")
    private String dsMensagem;
}