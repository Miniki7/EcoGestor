// Define o pacote da camada de serviço.
package api.service;

// CAMADA: Service
// RESPONSABILIDADE: avisar que uma meta ambiental não existe. Herda de
//                   RecursoNaoEncontradoException, por isso o ApiExceptionHandler
//                   já devolve 404 sem precisar mudar nada.
// SITUAÇÃO: implementada.
public class MetaAmbientalNaoEncontradaException extends RecursoNaoEncontradoException {

    // Cria a exceção usando o id que não foi encontrado.
    public MetaAmbientalNaoEncontradaException(Integer id) {
        // Envia a mensagem que vai aparecer no campo "mensagem" do ErroDTO.
        super("Meta ambiental não encontrada: " + id);
    }
}
