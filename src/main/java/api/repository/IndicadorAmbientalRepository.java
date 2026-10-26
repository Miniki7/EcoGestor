// Define o pacote responsável pelo acesso a dados.
package api.repository;

// Importa o tipo de data usado na consulta da regra R1.
import java.time.LocalDate;

// Importa a entidade que este repositório irá persistir.
import api.model.IndicadorAmbiental;
// Importa a interface pronta de operações CRUD do Spring Data JPA.
import org.springframework.data.jpa.repository.JpaRepository;

// CAMADA: Repository
// RESPONSABILIDADE: fornecer operações de persistência e consulta de IndicadorAmbiental, incluindo a verificação usada pela R1.
// SITUAÇÃO: implementada.
// Declara um repositório para a entidade IndicadorAmbiental.
public interface IndicadorAmbientalRepository extends JpaRepository<IndicadorAmbiental, Integer> {
    // O Spring cria a implementação em runtime usando IndicadorAmbiental como entidade e Integer como tipo do id.

    // R1: "Usuario" + "Id" navega pelo relacionamento --
    // vira WHERE id_usuario = ? AND mes_referencia = ?
    boolean existsByUsuarioIdAndMesReferenciaBetween(Integer usuarioId, LocalDate dataInicio, LocalDate dataFim);
}