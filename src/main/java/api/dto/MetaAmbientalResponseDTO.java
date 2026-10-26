// Declara o pacote dos DTOs -- as classes que formam o contrato da API.
package api.dto;

// Importa os tipos usados nos campos.
import java.math.BigDecimal;
import java.time.LocalDate;

// Importa a anotação que documenta cada campo no Swagger.
import io.swagger.v3.oas.annotations.media.Schema;

// CAMADA: DTO
// RESPONSABILIDADE: o que a API devolve sobre uma meta. Usuário e indicador
//                   saem só como id; o status nasce "pendente".
// SITUAÇÃO: implementada.
// Descreve o DTO inteiro na seção "Schemas" do Swagger.
@Schema(description = "Meta ambiental")
public record MetaAmbientalResponseDTO(

        @Schema(description = "Identificador da meta", example = "1")
        Integer id,

        @Schema(description = "Id do usuário dono da meta", example = "1")
        Integer idUsuario,

        @Schema(description = "Id do indicador ambiental monitorado", example = "1")
        Integer idIndicador,

        @Schema(description = "Descrição da meta", example = "Reduzir o consumo de água")
        String descricao,

        @Schema(description = "Valor definido para a meta", example = "400.0")
        BigDecimal valorMeta,

        @Schema(description = "Início da vigência", example = "2026-10-01")
        LocalDate dataInicio,

        @Schema(description = "Fim da vigência", example = "2026-12-31")
        LocalDate dataFim,

        @Schema(description = "Situação da meta", example = "pendente")
        String status

) {
}