package api.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

// CAMADA: Model
// RESPONSABILIDADE: uma pessoa cadastrada que pode adicionar indicadores e metas, gravado na tabela de usuarios (migration V1).
// SITUAÇÃO: implementada.
// Diz ao JPA que esta classe será persistida no banco.
@Entity
// Liga a entidade à tabela criada pela migration.
@Table(name = "usuarios")
public class Usuario {

    // Marca o atributo que representa a chave primária.
    @Id
    // Pede ao banco para gerar o id no momento do INSERT.
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(nullable = false, length = 150, unique = true)
    private String email;

    @Column(nullable = false, length = 255)
    private String senha;

    @Column(name = "tipo_usuario", nullable = false, length = 50)
    private String tipoUsuario;

    @Column(name = "data_cadastro", updatable = false)
    private LocalDateTime dataCadastro;

    // Lado inverso do relacionamento: a chave estrangeira fica na outra tabela.
    @OneToMany(mappedBy = "usuario")
    private List<IndicadorAmbiental> indicadores = new ArrayList<>();

    // Construtor sem argumentos exigido pelo Hibernate para reconstruir entidades.
    protected Usuario() {
    }

    // Construtor usado pela aplicação para criar um registro novo (id = null,
    // o banco gera no INSERT).
    public Usuario(String nome, String email, String senha, String tipoUsuario) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.tipoUsuario = tipoUsuario;
    }

    // Garante uma data mesmo quando o Hibernate usa o construtor protegido.
    @PrePersist
    private void definirDataCadastro() {
        if (dataCadastro == null) {
            dataCadastro = LocalDateTime.now();
        }
    }

    public Integer getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public LocalDateTime getDataCadastro() {
        return dataCadastro;
    }

    public List<IndicadorAmbiental> getIndicadores() {
        return indicadores;
    }
}