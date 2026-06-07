package br.com.fiap.orbitAlert.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Schema(description = "Entidade que representa a tabela TB_LEITURA_IOT no Oracle DB.")
@Entity
@Table(name = "TB_LEITURA_IOT")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class LeituraIot {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_leitura_iot")
    @SequenceGenerator(name = "seq_leitura_iot", sequenceName = "SEQ_LEITURA_IOT", allocationSize = 1)
    @Column(name = "ID_LEITURA")
    @Schema(description = "Identificador único da leitura IoT.")
    private Long id;

    @NotNull(message = "A temperatura é obrigatória")
    @Column(name = "NR_TEMPERATURA", nullable = false)
    @Schema(description = "Temperatura do ar em graus Celsius lida pelo DHT22.")
    private Double nrTemperatura;

    @NotNull(message = "A umidade é obrigatória")
    @DecimalMin(value = "0.0", message = "Umidade deve ser no mínimo 0%")
    @DecimalMax(value = "100.0", message = "Umidade deve ser no máximo 100%")
    @Column(name = "NR_UMIDADE", nullable = false)
    @Schema(description = "Umidade relativa do ar em % lida pelo DHT22.")
    private Double nrUmidade;

    @Column(name = "NR_CHUVA_MM", nullable = false)
    @Schema(description = "Precipitação acumulada em milímetros.")
    private Double nrChuvaMm = 0.0;

    @NotNull(message = "O índice de risco é obrigatório")
    @Min(value = 1, message = "O índice de risco deve ser no mínimo 1")
    @Max(value = 5, message = "O índice de risco deve ser no máximo 5")
    @Column(name = "NR_INDICE_RISCO", nullable = false)
    @Schema(description = "Índice de risco calculado pela API Java. Valores de 1 a 5.")
    private Integer nrIndiceRisco;

    @Column(name = "DT_LEITURA", nullable = false, updatable = false, insertable = false)
    @Schema(description = "Data e hora da leitura publicada pelo ESP32.")
    private LocalDateTime dtLeitura;

    @NotNull(message = "A estação é obrigatória")
    @ManyToOne
    @JoinColumn(name = "ID_ESTACAO", nullable = false)
    @Schema(description = "Estação ESP32 que originou a leitura.")
    private EstacaoIot estacaoIot;

    public void atualizar(LeituraIot leituraIot) {
        this.nrTemperatura = leituraIot.getNrTemperatura();
        this.nrUmidade = leituraIot.getNrUmidade();
        this.nrChuvaMm = leituraIot.getNrChuvaMm();
        this.nrIndiceRisco = leituraIot.getNrIndiceRisco();
        this.estacaoIot = leituraIot.getEstacaoIot();
    }
}