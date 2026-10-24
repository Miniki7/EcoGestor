// Declara o pacote dos DTOs -- as classes que formam o contrato da API.
package api.dto;

// Importa os tipos usados nos campos.
import java.math.BigDecimal;
import java.time.LocalDate;

// Importa as anotações de validação do corpo da requisição.
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

// Importa a anotação que documenta cada campo no Swagger.
import io.swagger.v3.oas.annotations.media.Schema;

// O que o cliente pode enviar para cadastrar uma meta.
@Schema(description = "Dados para cadastrar uma meta ambiental")
public record MetaAmbientalRequestDTO(

        // Relacionamentos entram como id.
        @Schema(description = "Id do usuário dono da meta", example = "1")
        @NotNull(message = "Usuário é obrigatório")
        Integer idUsuario,

        @Schema(description = "Id do indicador ambiental monitorado", example = "1")
        @NotNull(message = "Indicador é obrigatório")
        Integer idIndicador,

        @Schema(description = "Descrição da meta", example = "Reduzir o consumo de água")
        @NotBlank(message = "Descrição é obrigatória")
        String descricao,

        @Schema(description = "Valor definido para a meta", example = "400.0")
        @NotNull(message = "Valor da meta é obrigatório")
        @PositiveOrZero(message = "Valor da meta não pode ser negativo")
        BigDecimal valorMeta,

        @Schema(description = "Início da vigência (yyyy-MM-dd)", example = "2026-10-01")
        @NotNull(message = "Data de início é obrigatória")
        LocalDate dataInicio,

        @Schema(description = "Fim da vigência (yyyy-MM-dd)", example = "2026-12-31")
        @NotNull(message = "Data de fim é obrigatória")
        LocalDate dataFim

) {
}