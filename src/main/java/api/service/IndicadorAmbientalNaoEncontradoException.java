// Define o pacote da camada de serviço.
package api.service;

// CAMADA: Service
// RESPONSABILIDADE: avisar que um indicador ambiental não existe. Herda de
//                   RecursoNaoEncontradoException, por isso o ApiExceptionHandler
//                   já devolve 404 sem precisar mudar nada.
// SITUAÇÃO: implementada.
public class IndicadorAmbientalNaoEncontradoException extends RecursoNaoEncontradoException {

    // Cria a exceção usando o id que não foi encontrado.
    public IndicadorAmbientalNaoEncontradoException(Integer id) {
        // Envia uma mensagem explicativa para a classe-mãe.
        super("Indicador ambiental não encontrado: " + id);
    }
}