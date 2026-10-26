package api.mapper;

import org.springframework.stereotype.Component;
import java.util.Locale;

import api.dto.UsuarioRequestDTO;
import api.dto.UsuarioResponseDTO;
import api.model.Usuario;

// CAMADA: Mapper
// RESPONSABILIDADE: converter UsuarioRequestDTO em Usuario 
//                   e Usuario em UsuarioResponseDTO, sem incluir a senha na resposta.
// SITUAÇÃO: implementada.
@Component
public class UsuarioMapper {

    public Usuario toEntity(UsuarioRequestDTO dto) {
        return new Usuario(dto.nome(), dto.email().toLowerCase(Locale.ROOT), dto.senha(), dto.tipoUsuario());
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