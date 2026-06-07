package br.com.fiap.orbitAlert.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;

import java.time.LocalDate;

@MappedSuperclass
@Data
public abstract class BaseEntity {

    @Column(name = "DT_CADASTRO", nullable = false, updatable = false, insertable = false)
    @Schema(description = "Data de cadastro do registro. Gerada automaticamente pelo banco.")
    private LocalDate dtCadastro;
}