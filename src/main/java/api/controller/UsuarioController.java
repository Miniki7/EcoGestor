// Define o pacote responsável pelas rotas HTTP da aplicação.
package api.controller;

// Importa a lista devolvida na listagem.
import java.util.List;

// Importa as anotações de documentação do Swagger/OpenAPI.
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
// Importa a anotação que dispara a validação do DTO.
import jakarta.validation.Valid;

// Importa o status HTTP usado na criação.
import org.springframework.http.HttpStatus;
// Importa o tipo usado para responder com status e corpo.
import org.springframework.http.ResponseEntity;
// Importa as anotações de rota, de parâmetro e de corpo.
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// Importa os DTOs de entrada, de saída e de erro -- e só eles: nenhum import de api.model.
import api.dto.ErroDTO;
import api.dto.UsuarioRequestDTO;
import api.dto.UsuarioResponseDTO;
// Importa a camada que contém as regras do usuário.
import api.service.UsuarioService;

// CAMADA: Controller
// RESPONSABILIDADE: receber e validar as requisições de usuários e devolver
//                   respostas em DTOs com os status HTTP correspondentes.
// ROTAS ATENDIDAS: POST /usuarios, GET /usuarios e 
//                  GET /usuarios/{id}.
// SITUAÇÃO: implementada.
// Agrupa todos os endpoints desta classe sob "Usuários" no Swagger UI.
@Tag(name = "Usuários", description = "Cadastro e consulta de usuários")
// Informa ao Spring que a classe atende requisições REST.
@RestController
// Adiciona /usuarios antes de todas as rotas abaixo.
@RequestMapping("/usuarios")
public class UsuarioController {

    // Guarda a camada de serviço usada pelas rotas.
    private final UsuarioService service;

    // Recebe o service injetado pelo Spring.
    public UsuarioController(UsuarioService service) {
        // Guarda o service para uso nos métodos HTTP.
        this.service = service;
    }

    @PostMapping
    @ApiResponse(responseCode = "201", description = "Usuário cadastrado")
    @Operation(summary = "Cadastra um usuário")
    public UsuarioResponseDTO criar(@Valid @RequestBody UsuarioRequestDTO dto) {
        return service.criar(dto);
    }

    @GetMapping
    @Operation(summary = "Lista os usuários")
    @ApiResponse(responseCode = "200", description = "Lista de usuários")
    public List<UsuarioResponseDTO> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca um usuário pelo id")
    @ApiResponse(responseCode = "200", description = "Usuário encontrado")
    @ApiResponse(responseCode = "404", description = "Não existe usuário com esse id",
        content = @Content(schema = @Schema(implementation = ErroDTO.class)))
    public UsuarioResponseDTO buscar(
            @Parameter(description = "Id do usuário", example = "1") @PathVariable Integer id) {
        // Delega a busca para o service.
        return service.buscarPorId(id);
    }
}