
// Declara o pacote dos DTOs -- as classes que formam o contrato da API.
package api.dto;

// Importa o tipo de data e hora do cadastro.
import java.time.LocalDateTime;

// Importa a anotação que documenta cada campo no Swagger.
import io.swagger.v3.oas.annotations.media.Schema;

// CAMADA: DTO
// RESPONSABILIDADE: o que a API devolve sobre um usuário.
// SITUAÇÃO: implementada.
// Descreve o DTO inteiro na seção "Schemas" do Swagger.
@Schema(description = "Usuário cadastrado")
public record UsuarioResponseDTO(

        @Schema(description = "Identificador do usuário", example = "1")
        Integer id,

        @Schema(description = "Nome do usuário", example = "Maria Souza")
        String nome,

        @Schema(description = "E-mail do usuário", example = "maria@email.com")
        String email,

        @Schema(description = "Tipo do usuário", example = "pessoa física")
        String tipoUsuario,

        @Schema(description = "Momento do cadastro", example = "2026-10-23T19:30:00")
        LocalDateTime dataCadastro

) {
}