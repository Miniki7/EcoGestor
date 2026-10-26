package api.mapper;

import org.springframework.stereotype.Component;

import api.dto.UsuarioRequestDTO;
import api.dto.UsuarioResponseDTO;
import api.model.Usuario;

// Camada: Mapper
// Responsabilidade: converter UsuarioRequestDTO em Usuario e Usuario em UsuarioResponseDTO, sem incluir a senha na resposta.
@Component
public class UsuarioMapper {

    public Usuario toEntity(UsuarioRequestDTO dto) {
        return new Usuario(dto.nome(), dto.email(), dto.senha(), dto.tipoUsuario());
    }

    public UsuarioResponseDTO toResponse(Usuario usuario) {
        return new UsuarioResponseDTO(
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getTipoUsuario(),
                usuario.getDataCadastro());
    }
}