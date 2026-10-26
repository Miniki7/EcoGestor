package api.service;

import java.util.List;

import org.springframework.stereotype.Service;

import api.dto.UsuarioRequestDTO;
import api.dto.UsuarioResponseDTO;
import api.mapper.UsuarioMapper;
import api.model.Usuario;
import api.repository.UsuarioRepository;

// CAMADA: Service
// RESPONSABILIDADE: cadastrar, listar e buscar usuários, coordenando o Repository e o Mapper sem expor entidades ao Controller.
// REGRAS: nenhuma das R1 a R3. O e-mail único é restrição de integridade: recusado com 400 se já existir.
// ROTAS ATENDIDAS: POST /usuarios, GET /usuarios e 
//                  GET /usuarios/{id}.
// SITUAÇÃO: implementada.
@Service
public class UsuarioService {

    private final UsuarioRepository repository;
    private final UsuarioMapper mapper;

    public UsuarioService(UsuarioRepository repository, UsuarioMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public UsuarioResponseDTO criar(UsuarioRequestDTO dto) {
        // Checa email antes do save.
        if (repository.existsByEmailIgnoreCase(dto.email())) {
          throw new RegraDeNegocioException("Já existe um usuário cadastrado com o e-mail " + dto.email());
        }
        Usuario salvo = repository.save(mapper.toEntity(dto));
        return mapper.toResponse(salvo);
    }

    public List<UsuarioResponseDTO> listar() {
        return repository.findAll().stream().map(mapper::toResponse).toList();
    }

    public UsuarioResponseDTO buscarPorId(Integer id) {
        return mapper.toResponse(buscarEntidade(id));
    }

    // Reaproveitado pelos services de indicador e meta para validar o idUsuario (gera o 404 do contrato).
    public Usuario buscarEntidade(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new UsuarioNaoEncontradoException(id));
    }
}