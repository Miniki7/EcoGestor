// Define o pacote da camada de regras de negócio.
package api.service;

// Importa a lista devolvida nas consultas.
import java.util.List;

// Importa a anotação que registra a classe como serviço Spring.
import org.springframework.stereotype.Service;

// Importa os DTOs de entrada e de saída.
import api.dto.IndicadorAmbientalRequestDTO;
import api.dto.IndicadorAmbientalResponseDTO;
// Importa o mapper que traduz DTO <-> entidade.
import api.mapper.IndicadorAmbientalMapper;
// Importa as entidades persistidas.
import api.model.IndicadorAmbiental;
import api.model.Usuario;
// Importa os repositórios usados pelo service.
import api.repository.IndicadorAmbientalRepository;
import api.repository.UsuarioRepository;

// Camada: Service
// Responsabilidade: cadastrar, listar e buscar indicadores ambientais, aplicando as regras de negócio antes de persistir.
// Regras: R1 (um registro por usuário e mês de referência) e R2 (resíduos reciclados não podem superar os resíduos gerados).
// Rotas atendidas: POST /indicadores-ambientais, GET /indicadores-ambientais e GET /indicadores-ambientais/{id}.

// @Service: camada de regra de negócio -- o Controller fala com o Service,
// nunca direto com o Repository. As regras R1 e R2 vivem aqui.
@Service
public class IndicadorAmbientalService {

    // Acesso à tabela indicadores_ambientais.
    private final IndicadorAmbientalRepository repository;

    // Usado para buscar o usuário antes de vincular.
    private final UsuarioRepository usuarioRepository;

    // Converte DTO <-> entidade. Injetado pelo Spring.
    private final IndicadorAmbientalMapper mapper;

    // Recebe as dependências que o Spring injeta automaticamente.
    public IndicadorAmbientalService(IndicadorAmbientalRepository repository,
                                     UsuarioRepository usuarioRepository,
                                     IndicadorAmbientalMapper mapper) {
        // Guarda as dependências recebidas nos atributos da classe.
        this.repository = repository;
        this.usuarioRepository = usuarioRepository;
        this.mapper = mapper;
    }

    // Lista todos os indicadores.
    public List<IndicadorAmbientalResponseDTO> listarTodos() {
        // O Repository devolve entidades; o mapper converte a lista inteira.
        return mapper.toResponseList(repository.findAll());
    }

    // Cria um indicador a partir do DTO já validado, aplicando R1 e R2.
    public IndicadorAmbientalResponseDTO criar(IndicadorAmbientalRequestDTO dto) {
        // 1. O idUsuario vira entidade -- 404 se não existir.
        Usuario usuario = usuarioRepository.findById(dto.idUsuario())
                .orElseThrow(() -> new UsuarioNaoEncontradoException(dto.idUsuario()));
        // 2. R2: reciclados não podem superar gerados.
        if (dto.residuosReciclados().compareTo(dto.residuosGerados()) > 0) {
            throw new RegraDeNegocioException(
                    "R2: a quantidade de resíduos reciclados não pode superar a quantidade de resíduos gerados.");
        }
        // 3. R1: o usuário não pode ter dois registros no mesmo mês.
        if (repository.existsByUsuarioIdAndMesReferencia(dto.idUsuario(), dto.mesReferencia())) {
            throw new RegraDeNegocioException(
                    "R1: o usuário já possui um registro ambiental para este mês de referência.");
        }
        // 4. DTO + usuário -> entidade nova, e o INSERT acontece no save.
        IndicadorAmbiental salvo = repository.save(mapper.toEntity(dto, usuario));
        // 5. Entidade -> DTO de saída.
        return mapper.toResponse(salvo);
    }

    // Busca um indicador pelo id -- 404 (via exceção) se não existir.
    public IndicadorAmbientalResponseDTO buscarPorId(Integer id) {
        // Consulta o banco e transforma ausência em exceção (404).
        IndicadorAmbiental indicador = repository.findById(id)
                .orElseThrow(() -> new IndicadorAmbientalNaoEncontradoException(id));
        // Converte para o DTO de saída.
        return mapper.toResponse(indicador);
    }
}