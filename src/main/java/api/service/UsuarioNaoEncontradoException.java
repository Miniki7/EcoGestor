// Define o pacote da camada de serviço.
package api.service;

// CAMADA: Service
// RESPONSABILIDADE: avisar que um usuário não existe. Herda de
//                   RecursoNaoEncontradoException, por isso o ApiExceptionHandler
//                   já devolve 404 sem precisar mudar nada.
// SITUAÇÃO: implementada.
public class UsuarioNaoEncontradoException extends RecursoNaoEncontradoException {

    public UsuarioNaoEncontradoException(Integer id) {
        super("Usuário não encontrado: " + id);
    }
}