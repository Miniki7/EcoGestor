// Define o pacote responsável pelo acesso a dados.
package api.repository;

// Importa o tipo de data usado na consulta da regra R3.
import java.time.LocalDate;

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

    // R3: existe meta deste usuário, para este indicador, com período que se
    // sobrepõe ao novo? Dois períodos se sobrepõem quando cada um começa antes
    // (ou no mesmo dia) que o outro termina:
    //   inicio da existente <= fim da nova  E  fim da existente >= inicio da nova
    // Vira WHERE id_usuario = ? AND id_indicador = ? AND data_inicio <= ? AND data_fim >= ?
    // Os parâmetros entram na ordem em que aparecem no nome do método.
    boolean existsByUsuarioIdAndIndicadorIdAndDataInicioLessThanEqualAndDataFimGreaterThanEqual(
            Integer usuarioId, Integer indicadorId, LocalDate fimDaNova, LocalDate inicioDaNova);
}