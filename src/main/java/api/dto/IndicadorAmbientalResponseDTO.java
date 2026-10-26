// Declara o pacote dos DTOs -- as classes que formam o contrato da API.
package api.dto;

// Importa os tipos usados nos campos.
import java.math.BigDecimal;
import java.time.LocalDate;

// Importa a anotação que documenta cada campo no Swagger.
import io.swagger.v3.oas.annotations.media.Schema;

// CAMADA: DTO
// RESPONSABILIDADE: o que a API devolve sobre um indicador.
// SITUAÇÃO: implementada.
// Descreve o DTO inteiro na seção "Schemas" do Swagger.
@Schema(description = "Indicadores ambientais de um mês")
public record IndicadorAmbientalResponseDTO(

        @Schema(description = "Identificador do registro", example = "1")
        Integer id,

        @Schema(description = "Id do usuário dono do registro", example = "1")
        Integer idUsuario,

        @Schema(description = "Mês de referência", example = "2026-09-01")
        LocalDate mesReferencia,

        @Schema(description = "Consumo de água em metros cúbicos", example = "420.5")
        BigDecimal consumoAgua,

        @Schema(description = "Consumo de energia em kWh", example = "1850.0")
        BigDecimal consumoEnergia,

        @Schema(description = "Resíduos gerados em kg", example = "250.0")
        BigDecimal residuosGerados,

        @Schema(description = "Resíduos reciclados em kg", example = "180.0")
        BigDecimal residuosReciclados,

        @Schema(description = "Observação livre", example = "Mês com produção normal")
        String observacao

) {
}