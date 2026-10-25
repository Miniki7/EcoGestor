package api.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import api.dto.UsuarioRequestDTO;
import api.dto.UsuarioResponseDTO;
import api.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/usuarios")
@Tag(name = "Usuários", description = "Cadastro e consulta de usuários")
public class UsuarioController {

    private final UsuarioService service;

    public UsuarioController(UsuarioService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Cadastra um usuário")
    public UsuarioResponseDTO criar(@Valid @RequestBody UsuarioRequestDTO dto) {
        return service.criar(dto);
    }

    @GetMapping
    @Operation(summary = "Lista os usuários")
    public List<UsuarioResponseDTO> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca um usuário pelo ID")
    public UsuarioResponseDTO buscar(@PathVariable Integer id) {
        return service.buscarPorId(id);
    }
}