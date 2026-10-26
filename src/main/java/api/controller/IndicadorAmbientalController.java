// Define o pacote responsável pelas rotas HTTP da aplicação.
package api.controller;

// Importa a lista devolvida nas consultas.
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
// Importa a anotação que libera chamadas de outras origens.
import org.springframework.web.bind.annotation.CrossOrigin;
// Importa a anotação de leitura HTTP.
import org.springframework.web.bind.annotation.GetMapping;
// Importa a anotação para ler parâmetros do caminho.
import org.springframework.web.bind.annotation.PathVariable;
// Importa a anotação de criação HTTP.
import org.springframework.web.bind.annotation.PostMapping;
// Importa a anotação que lê o corpo JSON.
import org.springframework.web.bind.annotation.RequestBody;
// Importa a anotação que define o prefixo das rotas.
import org.springframework.web.bind.annotation.RequestMapping;
// Importa a anotação que registra a classe como controller REST.
import org.springframework.web.bind.annotation.RestController;

// Importa os DTOs de entrada e de saída -- e SÓ eles: nenhum import de api.model.
import api.dto.ErroDTO;
import api.dto.IndicadorAmbientalRequestDTO;
import api.dto.IndicadorAmbientalResponseDTO;
// Importa a camada que contém as regras do indicador.
import api.service.IndicadorAmbientalService;

// Camada: Controller
// Responsabilidade: receber as requisições de indicadores, validar o DTO de entrada e devolver respostas com os status HTTP corretos.
// Rotas atendidas: POST /indicadores-ambientais, GET /indicadores-ambientais e GET /indicadores-ambientais/{id}.

// Agrupa todos os endpoints desta classe sob "Indicadores ambientais" no Swagger UI.
@Tag(name = "Indicadores ambientais", description = "Registro mensal de consumo de água, energia e resíduos (regras R1 e R2)")
// Permite que o frontend local faça chamadas para a API.
@CrossOrigin(origins = "*")
// Informa ao Spring que a classe atende requisições REST.
@RestController
// Adiciona /indicadores-ambientais antes de todas as rotas abaixo.
@RequestMapping("/indicadores-ambientais")
public class IndicadorAmbientalController {

    // Guarda a camada de serviço usada pelas rotas.
    private final IndicadorAmbientalService service;

    // Recebe o service injetado pelo Spring.
    public IndicadorAmbientalController(IndicadorAmbientalService service) {
        // Guarda o service para uso nos métodos HTTP.
        this.service = service;
    }

    // Mapeia GET /indicadores-ambientais.
    @Operation(summary = "Lista os indicadores ambientais")
    @ApiResponse(responseCode = "200", description = "Lista de indicadores (pode vir vazia)")
    @GetMapping
    public List<IndicadorAmbientalResponseDTO> listar() {
        // Delega a listagem para a camada de serviço.
        return service.listarTodos();
    }

    // @Valid dispara a validação do DTO antes deste método rodar.
    @Operation(summary = "Cadastra o registro mensal de indicadores",
            description = "R1: um usuário não pode ter dois registros no mesmo mês. "
                    + "R2: resíduos reciclados não podem superar os gerados.")
    @ApiResponse(responseCode = "201", description = "Indicador criado -- o corpo já traz o id gerado")
    @ApiResponse(responseCode = "400", description = "Corpo inválido ou regra R1/R2 violada",
            content = @Content(schema = @Schema(implementation = ErroDTO.class)))
    @ApiResponse(responseCode = "404", description = "O idUsuario informado não existe",
            content = @Content(schema = @Schema(implementation = ErroDTO.class)))
    @PostMapping
    public ResponseEntity<IndicadorAmbientalResponseDTO> criar(@Valid @RequestBody IndicadorAmbientalRequestDTO dto) {
        // Pede ao service para criar e persistir o indicador validado.
        IndicadorAmbientalResponseDTO criado = service.criar(dto);
        // Retorna 201 Created junto do indicador criado.
        return ResponseEntity.status(HttpStatus.CREATED).body(criado);
    }

    // Mapeia GET /indicadores-ambientais/{id}.
    @Operation(summary = "Busca um indicador pelo id")
    @ApiResponse(responseCode = "200", description = "Indicador encontrado")
    @ApiResponse(responseCode = "404", description = "Não existe indicador com esse id",
            content = @Content(schema = @Schema(implementation = ErroDTO.class)))
    @GetMapping("/{id}")
    public IndicadorAmbientalResponseDTO buscarPorId(
            @Parameter(description = "Id do indicador", example = "1") @PathVariable Integer id) {
        // Delega a busca para o service.
        return service.buscarPorId(id);
    }
}