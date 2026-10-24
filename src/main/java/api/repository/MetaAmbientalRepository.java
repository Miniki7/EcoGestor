// Define o pacote responsável pelo acesso a dados.
package api.repository;

// Importa o tipo de data usado na consulta da regra R3.
import java.time.LocalDate;

// Importa a entidade que este repositório irá persistir.
import api.model.MetaAmbiental;
// Importa a interface pronta de operações CRUD do Spring Data JPA.
import org.springframework.data.jpa.repository.JpaRepository;

// Declara um repositório para a entidade MetaAmbiental.
public interface MetaAmbientalRepository extends JpaRepository<MetaAmbiental, Integer> {
    // O Spring cria a implementação em runtime usando MetaAmbiental como entidade e Integer como tipo do id.

    // R3: navega pelos relacionamentos "Usuario" e "Indicador" --
    // vira WHERE id_usuario = ? AND id_indicador = ? AND data_inicio = ? AND data_fim = ?
    boolean existsByUsuarioIdAndIndicadorIdAndDataInicioAndDataFim(
            Integer usuarioId, Integer indicadorId, LocalDate dataInicio, LocalDate dataFim);
}