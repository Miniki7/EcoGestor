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
import api.dto.MetaAmbientalRequestDTO;
import api.dto.MetaAmbientalResponseDTO;
// Importa a camada que contém as regras das metas.
import api.service.MetaAmbientalService;

// CAMADA: Controller
// RESPONSABILIDADE: receber as requisições de /metas-ambientais, validar o DTO
//                   de entrada e devolver o status certo. A regra R3 não está
//                   aqui: fica no MetaAmbientalService.
// ROTAS ATENDIDAS: POST /metas-ambientais, GET /metas-ambientais e
//                  GET /metas-ambientais/{id}.
// SITUAÇÃO: implementada.
// Agrupa todos os endpoints desta classe sob "Metas ambientais" no Swagger UI.
@Tag(name = "Metas ambientais", description = "Cadastro e consulta das metas de sustentabilidade, com a regra R3")
// Informa ao Spring que a classe atende requisições REST.
@RestController
// Adiciona /metas-ambientais antes de todas as rotas abaixo.
@RequestMapping("/metas-ambientais")
public class MetaAmbientalController {

    // Guarda a camada de serviço usada pelas rotas.
    private final MetaAmbientalService service;

    // Recebe o service injetado pelo Spring.
    public MetaAmbientalController(MetaAmbientalService service) {
        // Guarda o service para uso nos métodos HTTP.
        this.service = service;
    }

    // @Valid confere o formato; as regras de negócio quem confere é o Service.
    @Operation(summary = "Cadastra uma meta ambiental",
            description = "R3: o usuário só pode ter uma meta por indicador em cada mês coberto pelo período "
                    + "de vigência. O indicador precisa ser do usuário informado. "
                    + "id e status são definidos pela API: toda meta nasce \"pendente\".")
    @ApiResponse(responseCode = "201", description = "Meta cadastrada: o corpo já traz o id gerado")
    @ApiResponse(responseCode = "400", description = "Corpo inválido, período invertido, indicador de outro "
            + "usuário ou regra R3 violada (a mensagem diz qual)",
            content = @Content(schema = @Schema(implementation = ErroDTO.class)))
    @ApiResponse(responseCode = "404", description = "O usuário ou o indicador não existe",
            content = @Content(schema = @Schema(implementation = ErroDTO.class)))
    @PostMapping
    public ResponseEntity<MetaAmbientalResponseDTO> criar(@Valid @RequestBody MetaAmbientalRequestDTO dto) {
        // Delega para o service, que aplica as regras.
        MetaAmbientalResponseDTO criada = service.criar(dto);
        // Devolve 201 Created com a meta no corpo.
        return ResponseEntity.status(HttpStatus.CREATED).body(criada);
    }

    // Mapeia GET /metas-ambientais.
    @Operation(summary = "Lista as metas ambientais", description = "Sem metas cadastradas, a lista vem vazia, não 404.")
    @ApiResponse(responseCode = "200", description = "Lista de metas (pode vir vazia)")
    @GetMapping
    public List<MetaAmbientalResponseDTO> listar() {
        // Delega a listagem para o service.
        return service.listarTodos();
    }

    // Mapeia GET /metas-ambientais/{id}.
    @Operation(summary = "Busca uma meta ambiental pelo id")
    @ApiResponse(responseCode = "200", description = "Meta encontrada")
    @ApiResponse(responseCode = "404", description = "Não existe meta com esse id",
            content = @Content(schema = @Schema(implementation = ErroDTO.class)))
    @GetMapping("/{id}")
    public MetaAmbientalResponseDTO buscarPorId(
            @Parameter(description = "Id da meta", example = "1") @PathVariable Integer id) {
        // Delega a busca para o service; se não existir, a exceção vira 404.
        return service.buscarPorId(id);
    }
}
