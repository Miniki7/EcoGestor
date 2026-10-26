// Define o pacote da camada de regras de negócio.
package api.service;

// Importa a lista devolvida na listagem.
import java.time.YearMonth;
import java.util.List;

// Importa a anotação que registra a classe como serviço Spring.
import org.springframework.stereotype.Service;
// Importa a anotação que faz o método inteiro valer como uma operação só no banco.
import org.springframework.transaction.annotation.Transactional;

// Importa os DTOs de entrada e de saída da meta.
import api.dto.MetaAmbientalRequestDTO;
import api.dto.MetaAmbientalResponseDTO;
// Importa o mapper que traduz DTO <-> entidade.
import api.mapper.MetaAmbientalMapper;
// Importa as entidades envolvidas nas regras.
import api.model.IndicadorAmbiental;
import api.model.MetaAmbiental;
import api.model.Usuario;
// Importa os repositórios usados pelo service.
import api.repository.IndicadorAmbientalRepository;
import api.repository.MetaAmbientalRepository;
import api.repository.UsuarioRepository;

// CAMADA: Service
// RESPONSABILIDADE: cadastrar, listar e buscar metas ambientais. Recebe DTO,
//                   aplica as regras, usa o mapper e devolve DTO: a entidade
//                   MetaAmbiental não sobe para o Controller.
// REGRAS: R3 (um usuário só pode ter uma meta por indicador em cada mês coberto
//         pelo período de vigência).
// ROTAS ATENDIDAS: POST /metas-ambientais, GET /metas-ambientais e
//                  GET /metas-ambientais/{id}.
// SITUAÇÃO: implementada.
// Registra a classe como bean da camada de serviço.
@Service
public class MetaAmbientalService {

    // Acesso à tabela metas_ambientais.
    private final MetaAmbientalRepository repository;

    // Usado para buscar o usuário pelo id que chegou no DTO.
    private final UsuarioRepository usuarioRepository;

    // Usado para buscar o indicador pelo id que chegou no DTO.
    private final IndicadorAmbientalRepository indicadorRepository;

    // Converte DTO <-> entidade.
    private final MetaAmbientalMapper mapper;

    // Recebe as dependências que o Spring injeta automaticamente.
    public MetaAmbientalService(MetaAmbientalRepository repository, UsuarioRepository usuarioRepository,
                                IndicadorAmbientalRepository indicadorRepository, MetaAmbientalMapper mapper) {
        // Guarda as dependências recebidas nos atributos da classe.
        this.repository = repository;
        this.usuarioRepository = usuarioRepository;
        this.indicadorRepository = indicadorRepository;
        this.mapper = mapper;
    }

    // POST /metas-ambientais. @Transactional: as consultas das regras e o INSERT
    // acontecem numa transação só.
    @Transactional
    public MetaAmbientalResponseDTO criar(MetaAmbientalRequestDTO dto) {
        // Período invertido. Vem primeiro porque não depende de nada gravado no
        // banco. Sem isto, quem barraria seria o CHECK da migration V3, e o
        // cliente receberia um erro 500 fora do formato ErroDTO.
        if (dto.dataFim().isBefore(dto.dataInicio())) {
            // 400: o pedido veio no formato certo, mas o período não faz sentido.
            throw new RegraDeNegocioException("A data de fim (" + dto.dataFim()
                    + ") não pode ser anterior à data de início (" + dto.dataInicio() + ")");
        }

        // Entra um id, o Service busca a entidade: 404 se o usuário não existir.
        Usuario usuario = usuarioRepository.findById(dto.idUsuario())
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Usuário não encontrado: " + dto.idUsuario()));
        // O mesmo para o indicador.
        IndicadorAmbiental indicador = indicadorRepository.findById(dto.idIndicador())
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Indicador ambiental não encontrado: " + dto.idIndicador()));

        // O usuário aparece duas vezes no modelo: na meta e no indicador. Sem esta
        // conferência, uma meta do usuário 1 poderia apontar para o indicador do
        // usuário 2. equals, e não !=: com Integer, != compara objetos, não valores.
        if (!indicador.getUsuario().getId().equals(usuario.getId())) {
            // 400: os dois ids existem, mas não combinam entre si.
            throw new RegraDeNegocioException("O indicador ambiental " + indicador.getId()
                    + " não pertence ao usuário " + usuario.getId());
        }

        // R3: cada mês coberto pela vigência conta como ocupado para este usuário
        // e indicador, mesmo que as datas dentro do mês não coincidam.
        YearMonth inicioNovo = YearMonth.from(dto.dataInicio());
        YearMonth fimNovo = YearMonth.from(dto.dataFim());
        boolean jaExisteMetaNoPeriodo = repository
                .findAllByUsuarioIdAndIndicadorId(usuario.getId(), indicador.getId())
                .stream()
                .anyMatch(existente -> {
                    YearMonth inicioExistente = YearMonth.from(existente.getDataInicio());
                    YearMonth fimExistente = YearMonth.from(existente.getDataFim());
                    return !inicioExistente.isAfter(fimNovo) && !fimExistente.isBefore(inicioNovo);
                });
        if (jaExisteMetaNoPeriodo) {
            // A mensagem vai para o cliente, dentro do ErroDTO, com status 400.
            throw new RegraDeNegocioException("R3: o usuário " + usuario.getId()
                    + " já tem uma meta para o indicador " + indicador.getId()
                    + " em pelo menos um mês entre " + inicioNovo + " e " + fimNovo);
        }

        // O mapper monta a meta nova, já ligada ao usuário e ao indicador.
        MetaAmbiental meta = mapper.toEntity(dto, usuario, indicador);
        // O banco gera o id no INSERT e devolve a entidade preenchida.
        MetaAmbiental salva = repository.save(meta);
        // A entidade para aqui: para cima vai só o DTO.
        return mapper.toResponse(salva);
    }

    // GET /metas-ambientais: devolve todas as metas. Sem metas, a lista vem vazia.
    // readOnly: só leitura. A transação também mantém a sessão do Hibernate aberta
    // enquanto o mapper lê os relacionamentos (LAZY), porque o projeto usa
    // spring.jpa.open-in-view=false.
    @Transactional(readOnly = true)
    public List<MetaAmbientalResponseDTO> listarTodos() {
        // Busca todas e converte a lista inteira.
        return mapper.toResponseList(repository.findAll());
    }

    // GET /metas-ambientais/{id}: devolve uma meta ou lança a exceção que vira 404.
    @Transactional(readOnly = true)
    public MetaAmbientalResponseDTO buscarPorId(Integer id) {
        // findById devolve um Optional; se estiver vazio, lança a exceção em vez de devolver null.
        MetaAmbiental meta = repository.findById(id).orElseThrow(() -> new MetaAmbientalNaoEncontradaException(id));
        // Converte a meta encontrada.
        return mapper.toResponse(meta);
    }
}
