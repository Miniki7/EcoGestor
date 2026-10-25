// Define o pacote dos mappers.
package api.mapper;

// Importa a lista usada na conversão em lote.
import java.util.List;

// Importa a anotação que registra a classe como bean do Spring.
import org.springframework.stereotype.Component;

// Importa os DTOs de entrada e de saída da meta.
import api.dto.MetaAmbientalRequestDTO;
import api.dto.MetaAmbientalResponseDTO;
// Importa as entidades envolvidas na conversão.
import api.model.IndicadorAmbiental;
import api.model.MetaAmbiental;
import api.model.Usuario;

// CAMADA: Mapper
// RESPONSABILIDADE: converter MetaAmbientalRequestDTO em MetaAmbiental, e
//                   MetaAmbiental em MetaAmbientalResponseDTO. O usuário e o
//                   indicador chegam prontos: quem busca no banco é o
//                   MetaAmbientalService, porque o mapper não acessa o banco.
// SITUAÇÃO: implementada.
// @Component: o Spring cria um MetaAmbientalMapper e injeta no MetaAmbientalService.
@Component
public class MetaAmbientalMapper {

    // POST: monta uma meta nova. O DTO traz só os ids; usuário e indicador vêm
    // do Service. id e status não vêm do cliente: o banco gera o id e toda meta
    // nasce "pendente" (o @PrePersist da entidade define).
    public MetaAmbiental toEntity(MetaAmbientalRequestDTO dto, Usuario usuario, IndicadorAmbiental indicador) {
        // Usa o construtor de cadastro da entidade.
        return new MetaAmbiental(usuario, indicador, dto.descricao(), dto.valorMeta(),
                dto.dataInicio(), dto.dataFim());
    }

    // Toda resposta: copia da entidade só o que o contrato da API mostra.
    // Dos relacionamentos sai só o id, como está em docs/contrato-da-api.md.
    public MetaAmbientalResponseDTO toResponse(MetaAmbiental meta) {
        // Monta o record com os oito campos de saída.
        return new MetaAmbientalResponseDTO(
                meta.getId(),
                meta.getUsuario().getId(),
                meta.getIndicador().getId(),
                meta.getDescricao(),
                meta.getValorMeta(),
                meta.getDataInicio(),
                meta.getDataFim(),
                meta.getStatus());
    }

    // GET de listagem: converte cada meta da lista.
    public List<MetaAmbientalResponseDTO> toResponseList(List<MetaAmbiental> metas) {
        // Reaproveita o toResponse para cada item.
        return metas.stream().map(this::toResponse).toList();
    }
}
