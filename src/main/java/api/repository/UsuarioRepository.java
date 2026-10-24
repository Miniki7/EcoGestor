// Define o pacote responsável pelo acesso a dados.
package api.repository;

// Importa a entidade que este repositório irá persistir.
import api.model.Usuario;
// Importa a interface pronta de operações CRUD do Spring Data JPA.
import org.springframework.data.jpa.repository.JpaRepository;

// Declara um repositório para a entidade Usuario.
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    // O Spring cria a implementação em runtime usando Usuario como entidade e Integer como tipo do id.
}