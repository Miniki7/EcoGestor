// Define o pacote responsável pelo acesso a dados.
package api.repository;

// Importa a lista de metas retornada pela consulta da regra R3.
import java.util.List;

// Importa a entidade que este repositório irá persistir.
import api.model.MetaAmbiental;
// Importa a interface pronta de operações CRUD do Spring Data JPA.
import org.springframework.data.jpa.repository.JpaRepository;

// CAMADA: Repository
// RESPONSABILIDADE: ler e gravar metas na tabela metas_ambientais, e responder
//                   a consulta da regra R3.
// SITUAÇÃO: implementada.
// Declara um repositório para a entidade MetaAmbiental.
public interface MetaAmbientalRepository extends JpaRepository<MetaAmbiental, Integer> {
    // O Spring cria a implementação em runtime usando MetaAmbiental como entidade e Integer como tipo do id.

    // R3: devolve as metas do mesmo usuário e indicador para comparar os meses
    // cobertos pelos períodos de vigência.
    List<MetaAmbiental> findAllByUsuarioIdAndIndicadorId(Integer usuarioId, Integer indicadorId);
}