// Declara o pacote dos DTOs -- as classes que formam o contrato da API.
package api.dto;

// Importa os tipos usados nos campos.
import java.math.BigDecimal;
import java.time.LocalDate;

// Importa as anotações de validação do corpo da requisição.
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

// Importa a anotação que documenta cada campo no Swagger.
import io.swagger.v3.oas.annotations.media.Schema;

// CAMADA: DTO
// RESPONSABILIDADE: o que envia para cadastrar um indicador.
// SITUAÇÃO: implementada.
// Descreve o DTO inteiro na seção "Schemas" do Swagger.
@Schema(description = "Dados para registrar os indicadores ambientais de um mês")
public record IndicadorAmbientalRequestDTO(

        // Relacionamento entra como id.
        @Schema(description = "Id do usuário dono do registro", example = "1")
        @NotNull(message = "Usuário é obrigatório")
        Integer idUsuario,

        @Schema(description = "Mês de referência (yyyy-MM-dd)", example = "2026-09-01")
        @NotNull(message = "Mês de referência é obrigatório")
        LocalDate mesReferencia,

        @Schema(description = "Consumo de água em metros cúbicos", example = "420.5")
        @NotNull(message = "Consumo de água é obrigatório")
        @PositiveOrZero(message = "Consumo de água não pode ser negativo")
        BigDecimal consumoAgua,

        @Schema(description = "Consumo de energia em kWh", example = "1850.0")
        @NotNull(message = "Consumo de energia é obrigatório")
        @PositiveOrZero(message = "Consumo de energia não pode ser negativo")
        BigDecimal consumoEnergia,

        @Schema(description = "Resíduos gerados em kg", example = "250.0")
        @NotNull(message = "Resíduos gerados é obrigatório")
        @PositiveOrZero(message = "Resíduos gerados não pode ser negativo")
        BigDecimal residuosGerados,

        @Schema(description = "Resíduos reciclados em kg", example = "180.0")
        @NotNull(message = "Resíduos reciclados é obrigatório")
        @PositiveOrZero(message = "Resíduos reciclados não pode ser negativo")
        BigDecimal residuosReciclados,

        @Schema(description = "Observação livre (opcional)", example = "Mês com produção normal")
        String observacao

) {
}