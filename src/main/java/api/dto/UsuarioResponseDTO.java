
// Declara o pacote dos DTOs -- as classes que formam o contrato da API.
package api.dto;

// Importa o tipo de data e hora do cadastro.
import java.time.LocalDateTime;

// Importa a anotação que documenta cada campo no Swagger.
import io.swagger.v3.oas.annotations.media.Schema;

// O que a API devolve sobre um usuário (nunca inclui a senha).
@Schema(description = "Usuário cadastrado")
public record UsuarioResponseDTO(

        @Schema(description = "Identificador do usuário", example = "1")
        Integer id,

        @Schema(description = "Nome do usuário", example = "Maria Souza")
        String nome,

        @Schema(description = "E-mail do usuário", example = "maria@empresa.com")
        String email,

        @Schema(description = "Tipo do usuário", example = "GESTOR_AMBIENTAL")
        String tipoUsuario,

        @Schema(description = "Momento do cadastro", example = "2026-10-23T19:30:00")
        LocalDateTime dataCadastro

) {
}