package api.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

// CAMADA: Model
// RESPONSABILIDADE: uma meta de sustentabilidade de um usuário para um indicador
//                   ambiental, gravada na tabela metas_ambientais (migration V3).
//                   Tem dois relacionamentos: muitas metas para um usuário e
//                   muitas metas para um indicador.
// SITUAÇÃO: implementada.
@Entity
@Table(name = "metas_ambientais")
public class MetaAmbiental {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_indicador", nullable = false)
    private IndicadorAmbiental indicador;

    @Column(nullable = false, length = 200)
    private String descricao;

    @Column(name = "valor_meta", nullable = false, precision = 10, scale = 2)
    private BigDecimal valorMeta;

    @Column(name = "data_inicio", nullable = false)
    private LocalDate dataInicio;

    @Column(name = "data_fim", nullable = false)
    private LocalDate dataFim;

    @Column(nullable = false, length = 100)
    private String status;

    protected MetaAmbiental() {
    }

    public MetaAmbiental(Usuario usuario, IndicadorAmbiental indicador, String descricao,
                         BigDecimal valorMeta, LocalDate dataInicio, LocalDate dataFim) {
        this.usuario = usuario;
        this.indicador = indicador;
        this.descricao = descricao;
        this.valorMeta = valorMeta;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
    }

    // O banco tem DEFAULT 'pendente', mas o Hibernate envia o campo no INSERT;
    // se ficasse null, violaria o NOT NULL. Por isso o valor padrão é definido aqui.
    @PrePersist
    private void definirStatusPadrao() {
        if (status == null || status.isBlank()) {
            status = "pendente";
        }
    }

    public Integer getId() {
        return id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public IndicadorAmbiental getIndicador() {
        return indicador;
    }

    public void setIndicador(IndicadorAmbiental indicador) {
        this.indicador = indicador;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public BigDecimal getValorMeta() {
        return valorMeta;
    }

    public void setValorMeta(BigDecimal valorMeta) {
        this.valorMeta = valorMeta;
    }

    public LocalDate getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(LocalDate dataInicio) {
        this.dataInicio = dataInicio;
    }

    public LocalDate getDataFim() {
        return dataFim;
    }

    public void setDataFim(LocalDate dataFim) {
        this.dataFim = dataFim;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}