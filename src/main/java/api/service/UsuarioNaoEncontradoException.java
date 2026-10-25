package api.service;

// 404 de usuário: herda da classe-mãe, então o ApiExceptionHandler já trata sem mudança.
public class UsuarioNaoEncontradoException extends RecursoNaoEncontradoException {

    public UsuarioNaoEncontradoException(Integer id) {
        super("Usuário não encontrado: " + id);
    }
}