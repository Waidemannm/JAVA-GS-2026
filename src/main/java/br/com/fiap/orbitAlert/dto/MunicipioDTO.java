package br.com.fiap.orbitAlert.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "DTO da entidade Municipio.")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MunicipioDTO {

    @NotBlank(message = "O nome do município é obrigatório")
    @Schema(description = "Nome oficial do município.", example = "Petropolis")
    private String nmMunicipio;

    @NotBlank(message = "O estado é obrigatório")
    @Schema(description = "Nome do estado por extenso.", example = "Rio de Janeiro")
    private String nmEstado;

    @NotNull(message = "A latitude é obrigatória")
    @Schema(description = "Latitude do centroide do município em graus decimais.", example = "-22.5050")
    private Double nrLatitude;

    @NotNull(message = "A longitude é obrigatória")
    @Schema(description = "Longitude do centroide do município em graus decimais.", example = "-43.1789")
    private Double nrLongitude;

    @Schema(description = "População estimada conforme IBGE.", example = "306688")
    private Long nrPopulacao;

    @Schema(description = "Indica se o município está ativo para monitoramento. S = ativo, N = inativo.", example = "S")
    private String stAtivo = "S";
}