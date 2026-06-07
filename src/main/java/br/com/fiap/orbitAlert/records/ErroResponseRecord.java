package br.com.fiap.orbitAlert.records;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Record imutável de resposta padronizada de erro da API.")
public record ErroResponseRecord(

        @Schema(description = "Timestamp do erro.", example = "2026-05-31T14:22:00")
        String timestamp,

        @Schema(description = "Código HTTP do erro.", example = "404")
        int status,

        @Schema(description = "Descrição resumida do erro.", example = "Recurso não encontrado")
        String erro,

        @Schema(description = "Mensagem detalhada do erro.", example = "Alerta com ID 99 não encontrado.")
        String mensagem
) {}