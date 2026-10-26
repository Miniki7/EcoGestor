// Define o pacote da camada de serviço.
package api.service;

// Exceção própria para "indicador ambiental não existe" -- herda da classe-mãe,
// então o ApiExceptionHandler já responde 404.
public class IndicadorAmbientalNaoEncontradoException extends RecursoNaoEncontradoException {

    // Cria a exceção usando o id que não foi encontrado.
    public IndicadorAmbientalNaoEncontradoException(Integer id) {
        // Envia uma mensagem explicativa para a classe-mãe.
        super("Indicador ambiental não encontrado: " + id);
    }
}