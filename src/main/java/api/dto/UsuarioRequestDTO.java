// Declara o pacote dos DTOs -- as classes que formam o contrato da API.
package api.dto;

// Importa as anotações de validação do corpo da requisição.
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

// Importa a anotação que documenta cada campo no Swagger.
import io.swagger.v3.oas.annotations.media.Schema;

// O que o cliente pode enviar para cadastrar um usuário.
@Schema(description = "Dados para cadastrar um usuário")
public record UsuarioRequestDTO(

        @Schema(description = "Nome do usuário", example = "Maria Souza")
        @NotBlank(message = "Nome é obrigatório")
        String nome,

        @Schema(description = "E-mail do usuário (único)", example = "maria@empresa.com")
        @NotBlank(message = "E-mail é obrigatório")
        @Email(message = "E-mail inválido")
        String email,

        @Schema(description = "Senha do usuário", example = "123456")
        @NotBlank(message = "Senha é obrigatória")
        String senha,

        @Schema(description = "Tipo do usuário", example = "GESTOR_AMBIENTAL")
        @NotBlank(message = "Tipo de usuário é obrigatório")
        String tipoUsuario

) {
}